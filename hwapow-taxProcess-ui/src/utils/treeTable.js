import { isEmpty } from '@/utils/validate'

/**
 * this在class的构造函数constructor中指向的是自身，
 * 在class的方法中指向的是运行时的环境，
 * 为正确使用this，定义me变量，在constructor中将class的this赋给me
 */
let me

/**
 * 处理树形结构表格存在全选或全部取消选中时不能对子节点行进行操作的问题。
 * 此类会自动为表格绑定select、selectAll、expandChange事件，
 * 表格必须在html中给expandRowKeys属性绑定一个数组
 * 同时，在html中也可以同时绑定select、selectAll事件，执行时系统会先执行html中绑定的事件，再执行类中的事件
 * 表格需要配置，rowKey和treeProp.children值，
 * 此方法应放在mounted中
 * @param tableObject 表格对象
 * @param treePidField 数据的上级ID字段，为空时默认pid
 * @param initSelection 初始化时选中的行
 * @param rootObject 页面根元素，固定传this即可
 *
 */
export class TreeTable {
  constructor(tableObject, treePidField, rootObject) {
    tableObject.$on('select', this.handleSelect)
    tableObject.$on('select-all', this.handleSelectAll)
    tableObject.$on('expand-change', this.handleExpandChange)

    me = this
    me.tree = tableObject
    me.root = rootObject
    me.treeOptions = {
      rowKey: tableObject.rowKey,
      childrenKey: tableObject.treeProps.children,
      rowParentKey: treePidField || tableObject.rowParentKey || 'pid'
    }

    if (!me.treeOptions.rowKey) {
      throw '表格rowKey属性未设置'
    }
  }
}

TreeTable.tree = null
TreeTable.root = null
TreeTable.treeOptions = {
  rowKey: null,
  childrenKey: null,
  rowParentKey: null
}
TreeTable.data = null
TreeTable.handleSelectChild = null

TreeTable.prototype = {
  //初始化已选中的节点
  initSelection(selectionRows) {
    const data = me.tree.data
    if (!selectionRows || selectionRows.length === 0 || !data || data.length === 0) {
      return
    }

    let selectionNode = []
    let key = me.treeOptions.rowKey
    selectionRows.map(row => {
      let node = me.findNode(row[key], data)
      if (node) {
        selectionNode.push(node)
      }
    })

    if (selectionNode.length > 0) {
      me.root.$nextTick(() => {
        selectionNode.map(node => {
          me.tree.toggleRowSelection(node, true)
        })
      })
    }
  },
  handleExpandChange(node, expanded) {
    let index = me.tree.expandRowKeys.indexOf(node[me.treeOptions.rowKey])
    if (expanded && index < 0) {
      me.tree.expandRowKeys.push(node[me.treeOptions.rowKey])
      return
    }

    if (!expanded && index >= 0) {
      me.tree.expandRowKeys.splice(index, 1)
    }
  },
  //设置所有行展开状态
  //expandRowLevel展开的行级次，省略时视为不展开，为all时展开所有级次，为大于零的数字时展开到相应级次
  //初始化时需要在$nextTick中执行
  expandNodes(expanded, expandRowLevel) {
    if (!expandRowLevel) {
      return
    }

    let level = 0
    if (expandRowLevel !== 'all') {
      level = Number(level)
      if (level < 1) {
        return
      }
    }

    const data = me.tree.data

    data.map(node => {
      if (node.children && node.children.length > 0) {
        me.toggleExpandNode(node, expanded, expandRowLevel === 'all' ? expandRowLevel : (level - 1))
      }
    })
  },
  /**
   * 展开或收缩节点
   * @param node 要展开或收缩的节点
   * @param expanded true展开，false收缩
   * @param expandLevel 从当前节点为开始向下要执行展开的级次，收缩时只针对本级次有效，可省略，默认为1
   */
  toggleExpandNode(node, expanded, expandLevel) {
    let level = expandLevel
    if (!level) level = 1
    if (level !== 'all') {
      level = Number(level)
      if (isNaN(level) || level <= 0)
        return
    }

    if (!node.children || node.children.length === 0) {
      return
    }

    me.tree.toggleRowExpansion(node, expanded)

    //收起
    if (!expanded || (level !== 'all' && level <= 1)) {
      return
    }

    node.children.map(childNode => {
      if (childNode.children && childNode.children.length > 0 && (level === 'all' || level > 1)) {
        me.toggleExpandNode(childNode, expanded, level === 'all' ? level : level - 1)
      }
    })
  },
  /**
   * 返回节点的展开状态，true展开，false收缩
   * @param node
   */
  isExpand(node){
    return me.tree.expandRowKeys && me.tree.expandRowKeys.includes(node[me.treeOptions.rowKey])
  },
  // 选中父节点时，子节点一起选中取消
  handleSelect(selection, row) {
    //获取当前的操作是勾选还是取消勾选，取消勾选时selection中不包含row
    let rowKey = me.treeOptions.rowKey
    let rowParentKey = me.treeOptions.rowParentKey
    let childrenKey = me.treeOptions.childrenKey
    const checked = selection.some(el => {
      return row[rowKey] === el[rowKey]
    })
    if (row[childrenKey] && row[childrenKey].length > 0) {
      me.setChildren(row[childrenKey], checked)
    }

    //设置父节点的选中与取消
    if (!isEmpty(row[rowParentKey])) {
      me.setParentSelected(row[rowParentKey])
    }
  },
  // 选择全部
  handleSelectAll(selection) {
    // tableData第一层只要有在selection里面就是全选
    const data = me.tree.data
    let key = me.treeOptions.rowKey
    let childKey = me.treeOptions.childrenKey
    const tableDataIds = data.map(row => row[key])
    const checked = selection.some(el => {
      return tableDataIds.includes(el[key])
    })

    let mapData = checked ? selection : data
    mapData.map(el => {
      if (el[childKey] && el[childKey].length > 0) {
        me.setChildren(el[childKey], checked)
      }
    })
  },
  /**
   * 选中或取消选中行
   * @param row
   * @param select
   */
  toggleSelection(row, select) {
    me.tree.toggleRowSelection(row, select)
  },
  /**
   * 设置子节点选中状态
   * @param children 节点数组
   * @param selected 是否选中
   */
  setChildren(children, selected) {
    const key = me.treeOptions.rowKey
    const childKey = me.treeOptions.childrenKey
    // 编辑多个子层级
    children.map((child) => {
      if(selected){
        const isSelected = me.tree.selection.some(node => {
          return node[key] === child[key]
        })
        if(!isSelected) {
          me.toggleSelection(child, selected)
          if(me.handleSelectChild) me.handleSelectChild(child, selected)
        }
      }else {
        me.toggleSelection(child, selected)
        if (me.handleSelectChild) me.handleSelectChild(child, selected)
      }

      if (child[childKey]) {
        me.setChildren(child[childKey], selected)
      }
    })
  },
  //设置父节点的选择状态，父节点下只要有选中的即为选中状态，否则未选中
  //此方法需多次遍历，效率较低
  setParentSelected(parentId) {
    const data = me.tree.data
    let node = me.findParentNode(parentId, data)
    if (!node) return

    let key = me.treeOptions.rowKey
    let parentKey = me.treeOptions.rowParentKey
    let childKey = me.treeOptions.childrenKey

    const nodeIds = node[childKey].map(row => row[key])
    const checked = me.tree.selection.some(el => {
      return nodeIds.includes(el[key])
    })

    me.toggleSelection(node, checked)

    if (node[parentKey]) {
      me.setParentSelected(node[parentKey])
    }
  },
  //查找父级节点
  findParentNode(parentId, nodeList) {
    let parentNode
    let key = me.treeOptions.rowKey
    let childKey = me.treeOptions.childrenKey
    for (let node of nodeList) {
      if (node[key] === parentId) {
        parentNode = node
        break
      }

      if (node[childKey] && node[childKey].length > 0) {
        parentNode = me.findParentNode(parentId, node[childKey])
        if (parentNode) {
          break
        }
      }
    }

    return parentNode
  },
  //查询节点
  findNode(nodeId, nodeList) {
    let result
    let key = me.treeOptions.rowKey
    let childKey = me.treeOptions.childrenKey
    for (let node of nodeList) {
      if (node[key] === nodeId) {
        result = node
        break
      }

      if (node[childKey] && node[childKey].length > 0) {
        result = me.findNode(nodeId, node[childKey])
        if (result) break
      }
    }

    return result
  }
}

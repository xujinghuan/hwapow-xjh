<template>
  <el-container class="fullContainer" style="height:65%">
    <div class="toolbar">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['process:process:add']"
      >新增节点</el-button>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="nodeList" :highlight-current-row="true" @row-click="handleSelectionChange">
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
          <template scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="所属流程" align="center" prop="processName" width="100"/>
        <el-table-column label="节点编码" align="center" prop="code" width="100"/>
        <el-table-column label="节点名称" align="center" prop="name" width="120"/>
        <el-table-column label="角色" align="center" prop="roleName" width="100"/>
        <el-table-column label="排序" align="center" prop="sortIndex" width="50"/>
        <el-table-column label="开始节点" align="center" prop="isStart" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isStart==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束节点" align="center" prop="isEnd" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isEnd==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="允许修改" align="center" prop="isAllowAlter" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isAllowAlter==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="允许删除" align="center" prop="isAllowDelete" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isAllowDelete==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="会签通过" align="center" prop="isAllPass"  width="50">
          <template slot-scope="scope">
            <span>{{ scope.row.isAllPass==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="会签退回" align="center" prop="isAllQuit"  width="50">
          <template slot-scope="scope">
            <span>{{ scope.row.isAllQuit==1?"是":"否"}}</span>
          </template>
        </el-table-column>
        <el-table-column label="接收组织类型" align="center" prop="receiveOrgType" >
          <template slot-scope="scope">
            <span>{{ getReceiveOrgTypeValue(scope.row.receiveOrgType) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['process:process:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['process:process:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer style="padding:0px">
      <el-header style="height: 30px;background: #e8f4ff;">
        <span style="line-height: 30px;font-family: cursive;font-weight: 700;">节点流转</span>
      </el-header>
      <el-main>
        <to-node :from-node-id="selectNodeId" :node-list="nodeList"></to-node>
      </el-main>
    </el-footer>
    <!-- 添加或修改流程节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px"  append-to-body :close-on-click-modal="false">
      <el-container  class="fullContainer" direction="vertical"  style="height:600px">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="节点编码" prop="code">
                  <el-input v-model="form.code" placeholder="请输入节点编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="节点名称" prop="name">
                  <el-input v-model="form.name" placeholder="请输入节点名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="开始节点" prop="isStart">
                  <el-radio-group v-model="form.isStart">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="结束节点" prop="isEnd">
                  <el-radio-group v-model="form.isEnd">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="允许修改" prop="isAllowAlter">
                  <el-radio-group v-model="form.isAllowAlter">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="允许删除" prop="isAllowDelete">
                  <el-radio-group v-model="form.isAllowDelete">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="接收组织" prop="receiveOrgType">
                  <el-select v-model="form.receiveOrgType" placeholder="请选择接收组织类型" clearable="true">
                    <el-option label="申请人组织" value="1" />
                    <el-option label="申请人第一级上级组织" value="2" />
                    <el-option label="申请人第二级上级组织" value="3" />
                    <el-option label="申请人第三级上级组织" value="4" />
                    <el-option label="自定义组织" value="5" />
                    <el-option label="指定组织" value="9" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="操作角色" prop="roleId">
                  <el-select v-model="form.roleId" placeholder="请选择" clearable="true">
                    <el-option
                      v-for="item in roleList"
                      :key="item.roleId"
                      :label="item.roleName"
                      :value="item.roleId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="数据权限" v-show="form.receiveOrgType == '9'">
              <el-checkbox v-model="orgExpand" @change="handleCheckedTreeExpand($event)">展开/折叠</el-checkbox>
              <el-checkbox v-model="orgNodeAll" @change="handleCheckedTreeNodeAll($event)">全选/全不选</el-checkbox>
              <el-checkbox v-model="orgCheckStrictly" @change="handleCheckedTreeConnect($event)">父子联动</el-checkbox>
              <el-tree
                class="tree-border"
                :data="orgOptions"
                show-checkbox
                default-expand-all
                ref="org"
                node-key="id"
                :check-strictly="!orgCheckStrictly"
                empty-text="加载中，请稍后"
                :props="defaultProps"
              ></el-tree>
            </el-form-item>
            <div v-show="form.receiveOrgType == '5'" style="padding-left: 91px;line-height: 14px;font-size: 12px;color: #ff0000;">自定义接收组织sql语句中不可以包含update和delete语句，参数使用'{billId}'代表单据Id，返回值必须为组织机构id（可以返回多个组织结构id）。</div>
            <el-form-item label="自定义接收组织sql" prop="receiveOrgSql" v-show="form.receiveOrgType == '5'">
              <el-input v-model="form.receiveOrgSql" type="textarea" :autosize="{ minRows: 7}" placeholder="请输入内容" />
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="各组织会签通过" prop="isAllPass">
                  <el-radio-group v-model="form.isAllPass">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="各组织会签退回" prop="isAllQuit">
                  <el-radio-group v-model="form.isAllQuit">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col :span="12">
              <el-form-item label="排序" prop="sortIndex">
                <el-input-number v-model="form.sortIndex" :precision="0" :step="1" :max="100"></el-input-number>
              </el-form-item>
            </el-col>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import { listNode, getNode, delNode, addNode, updateNode, receiveOrgTreeselect } from "@/api/process/node";
import { listRole } from '@/api/system/role'
import { treeselect as orgTreeselect } from '@/api/system/org'
import ToNode from '@/views/process/process/toNode'

export default {
  name: "Node",
  components: {
    ToNode
  },
  props: {
    processId:null
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      //选择的节点
      selectNodeId:null,
      // 总条数
      total: 0,
      // 流程节点表格数据
      nodeList: [],
      //选择的角色列表
      roleList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: { },
      // 组织列表
      orgOptions: [],
      // 表单参数
      form: {},
      //父子联动
      orgCheckStrictly:false,
      defaultProps: {
        children: "children",
        label: "label"
      },
      orgExpand: true,
      orgNodeAll: false,
      // 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        code: [
          { required: true, message: "编码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getRoleList();
    this.getOrgTreeselect();
  },
  methods: {
    /** 查询流程节点列表 */
    getList() {
      if(!this.processId){
        this.nodeList =[];
        this.loading = false;
        return;
      }
      this.loading = true;
      this.queryParams.processId=this.processId;
      listNode(this.queryParams).then(response => {
        this.nodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询组织树结构 */
    getOrgTreeselect() {
      orgTreeselect().then(response => {
        this.orgOptions = response.data;
      });
    },
    getRoleList(){
      listRole().then(response => {
        this.roleList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        processId: null,
        roleId: null,
        sortIndex: null,
        isStart: "0",
        isEnd: "0",
        isAllowAlter: "0",
        isAllowDelete: "0",
        receiveOrgSql: null,
        isAllPass: "0",
        isAllQuit:"0",
        delFlag: null,
        receiveOrgType: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
      if (this.$refs.org != undefined) {
        this.$refs.org.setCheckedKeys([]);
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      if(!this.processId){
        this.msgError("请选择左侧流程！");
        return;
      }
      this.reset();
      this.open = true;
      this.title = "添加流程节点";
    },
    /** 根据ID查询组织树结构 */
    getReceiveOrgTreeselect(nodeId) {
      return receiveOrgTreeselect(nodeId).then(response => {
        return response;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      const receiveOrgTreeselect = this.getReceiveOrgTreeselect(id);
      getNode(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程节点";
        this.$nextTick(() => {
          receiveOrgTreeselect.then(res => {
            this.$refs.org.setCheckedKeys(res.data);
          });
        });
      });
    },
    // 所有组织节点数据
    getOrgAllCheckedKeys() {
      // 目前被选中的组织节点
      let checkedKeys = this.$refs.org.getCheckedKeys();
      // 半选中的组织节点
      let halfCheckedKeys = this.$refs.org.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 提交按钮 */
    submitForm() {
      if(!this.processId){
        this.msgError("请选择左侧流程！");
        return;
      }
      if(this.form.isAllPass=="1"&&this.form.isAllQuit=="1"){
        this.msgError("不可以同时会签通过和退回！");
        return;
      }
      if(this.form.receiveOrgType=="5"){
        if(!this.form.receiveOrgSql){
          this.msgError("接收组织类型为自定义，自定义接收组织sql不可以为空！");
          return;
        }
      }
      this.form.receiveOrgIds = this.getOrgAllCheckedKeys();
      if(this.form.receiveOrgType=="9"){
        if(!this.form.receiveOrgIds||this.form.receiveOrgIds.length==0){
          this.msgError("接收组织类型为指定组织时，指定组织不可以为空！");
          return;
        }
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.processId=this.processId;
          if (this.form.id != null) {
            updateNode(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if(!this.processId){
              this.msgError("请选择左侧流程！");
              return;
            }
            addNode(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id;
      this.$confirm('是否确认删除流程节点编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delNode(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    getReceiveOrgTypeValue(type){
      switch (type){
        case "1":
          return "申请人组织";
        case "2" :
          return "申请人第一级上级组织";
        case "3":
          return "申请人第二级上级组织";
        case "4":
          return "申请人第三级上级组织";
        case "5":
          return "自定义组织";
        case "9":
          return "指定组织";
      }
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value) {
      let treeList = this.orgOptions;
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.org.store.nodesMap[treeList[i].id].expanded = value;
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value) {
      this.$refs.org.setCheckedNodes(value ? this.orgOptions: []);
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value) {
      this.orgCheckStrictly = value ? true: false;
    },
    handleSelectionChange(row){
      this.selectNodeId=row.id;
    }
  },
  watch:{
    processId(){
      this.getList();
      this.selectNodeId=null;
    }
  }
};
</script>

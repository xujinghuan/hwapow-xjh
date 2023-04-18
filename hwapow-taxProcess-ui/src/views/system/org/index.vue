<template>
  <el-container class="fullContainer">
    <el-aside width="330px">
      <el-container class="fullContainer">
        <el-header style="height: auto;">
          <el-input
            v-model="orgName"
            placeholder="请输入组织名称"
            clearable
            prefix-icon="el-icon-search"
            style="margin-bottom: 3px;width:200px"
          />
        </el-header>
        <el-main>
          <el-container class="fullContainer">
            <el-main>
              <el-tree
                style="height: calc(100% - 40px)"
                :data="orgTreeOptions"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                :default-expanded-keys="expandId"
                node-key="id"
                ref="tree"
                @node-click="handleNodeClick"
              />
            </el-main>
          </el-container>
        </el-main>
      </el-container>
    </el-aside>
    <el-main>
      <el-container class="fullContainer">
        <el-header>
          <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true"
                   v-show="showSearch">
            <el-form-item label="组织名称" prop="orgName">
              <el-input
                v-model="queryParams.orgName"
                placeholder="请输入组织名称"
                clearable
                style="width: 230px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="组织状态" clearable style="width: 230px">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-header>
        <div class="toolbar">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:org:add']"
          >新增
          </el-button>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </div>
        <el-main>
          <el-table
            v-loading="loading"
            :data="orgList"
            row-key="orgId"
          >
            <el-table-column prop="orgName" label="组织名称" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.orgCode + "-" + scope.row.orgName }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="orderNum" label="排序" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" :formatter="statusFormat" width="60"></el-table-column>
            <el-table-column label="组织类型" align="center" prop="orgType" width="80">
              <template slot-scope="scope">
                <span>{{ scope.row.orgType == 1 ? "单位" : "部门" }}</span>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createTime" width="200">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:org:edit']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-plus"
                  @click="handleAdd(scope.row)"
                  v-hasPermi="['system:org:add']"
                >新增
                </el-button>
                <el-button
                  v-if="scope.row.parentId != 0"
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:org:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
        <el-footer>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-footer>
      </el-container>
    </el-main>
    <!-- 添加或修改组织对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="24" v-if="form.parentId !== 0">
                <el-form-item label="上级组织" prop="parentId">
                  <treeselect class="vue-treeselect-mini" v-model="form.parentId" :options="orgOptions"
                              :normalizer="normalizer" placeholder="选择上级组织"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="组织名称" prop="orgName">
                  <el-input v-model="form.orgName" placeholder="请输入组织名称"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="组织编码" prop="orgCode">
                  <el-input v-model="form.orgCode" placeholder="请输入组织编码"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="区划" prop="regCode">
                  <el-input v-model="form.regCode" placeholder="请输入区划"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="显示排序" prop="orderNum">
                  <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="负责人" prop="leader">
                  <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="组织状态">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in statusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictValue"
                    >{{ dict.dictLabel }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="组织类型">
                  <el-radio-group v-model="form.orgType">
                    <el-radio label="1">单位</el-radio>
                    <el-radio label="2">部门</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="34">
                <el-form-item label="组织级别">
                  <el-radio-group v-model="form.orgLevel">
                    <el-radio label="1">省级</el-radio>
                    <el-radio label="2">市级</el-radio>
                    <el-radio label="3">县级</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {listOrg, getOrg, delOrg, addOrg, updateOrg, listOrgExcludeChild, listManageOrg} from "@/api/system/org";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";
import {treeselect} from "@/api/system/org";


export default {
  name: "Org",
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      rowKeyArray: [],
      // 表格树数据
      orgList: [],
      // 组织树选项
      orgOptions: [],
      //左侧树
      orgTreeOptions: [],
      // 弹出层标题
      title: "",
      // 组织名称
      orgName: undefined,
      total: 0,
      // 是否显示弹出层
      open: false,
      expandId: [],
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 15,
        parentId: 0,
        orgName: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        parentId: [
          {required: true, message: "上级组织不能为空", trigger: "blur"}
        ],
        orgName: [
          {required: true, message: "组织名称不能为空", trigger: "blur"}
        ],
        orderNum: [
          {required: true, message: "显示排序不能为空", trigger: "blur"}
        ],
        orgType: [
          {required: true, message: "组织机构类型不能为空", trigger: "blur"}
        ],
        orgCode: [
          {required: true, message: "组织机构编码不能为空", trigger: "blur"}
        ],
        regCode: [
          {required: true, message: "区划不能为空", trigger: "blur"}
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.parentId = data.id;
      this.getList();
    },
    /** 查询组织列表 */
    getList() {
      this.loading = true;
      listManageOrg(this.queryParams).then(response => {
        this.orgList = response.rows
        this.total = response.total
        this.loading = false;
      });
    },
    /** 转换组织数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.orgId,
        label: node.orgName,
        children: node.children
      };
    },
    // 字典状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orgId: undefined,
        parentId: undefined,
        orgName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        orgCode: null,
        regCode: null,
        status: "0",
        orgLevel: '3'
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.orgId;
      }
      this.open = true;
      this.title = "添加组织";
      listOrg().then(response => {
        this.orgOptions = this.handleTree(response.data, "orgId");
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getOrg(row.orgId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改组织";
      });
      listOrgExcludeChild(row.orgId).then(response => {
        this.orgOptions = this.handleTree(response.data, "orgId");
      });
    },
    /** 查询组织下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.orgTreeOptions = response.data;
        if (response.data.length > 0) {
          let arr=[]
          arr.push(this.orgTreeOptions[0].id)
          this.expandId=arr
        }
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orgId != undefined) {
            updateOrg(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getTreeselect();
            });
          } else {
            addOrg(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getTreeselect();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除名称为"' + row.orgName + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delOrg(row.orgId);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    }
  }
};
</script>

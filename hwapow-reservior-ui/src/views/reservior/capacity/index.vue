<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="水位" prop="waterLevel">
          <el-input
            v-model="queryParams.waterLevel"
            placeholder="请输入水位"
            onkeyup="value=value.replace(/[^\.\d]/g,'')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="库容" prop="capacity">
          <el-input
            v-model="queryParams.capacity"
            placeholder="请输入水位对应的库容"
            clearable
            onkeyup="value=value.replace(/[^\.\d]/g,'')"
            @keyup.enter.native="handleQuery"
          />
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
        v-hasPermi="['reservior:capacity:add']"
      >新增
      </el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['reservior:capacity:edit']"
      >修改
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['reservior:capacity:remove']"
      >删除
      </el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['reservior:capacity:export']"
      >导出
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="capacityList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
          <template scope="scope">
            <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="水位（米）" align="center" prop="waterLevel"/>
        <el-table-column label="库容（立方米）" align="center" prop="capacity"/>
        <el-table-column label="水库" align="center" prop="orgName"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['reservior:capacity:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['reservior:capacity:remove']"
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
    <!-- 添加或修改库容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="350px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="水位(米)" prop="waterLevel">
              <el-input
                v-model="form.waterLevel"
                placeholder="水位(米)"
                clearable
                onkeyup="value=value.replace(/[^\.\d]/g,'')"
              />
            </el-form-item>
            <el-form-item label="库容" prop="capacity">
              <el-input
                v-model="form.capacity"
                placeholder="库容"
                clearable
                onkeyup="value=value.replace(/[^\.\d]/g,'')"
              />
            </el-form-item>
            <el-form-item label="归属水库" prop="orgId">
              <treeselect class="vue-treeselect-mini" v-model="form.orgId" :options="orgOptions" :show-count="true" placeholder="请选择归属水库" />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注"/>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {
  listCapacity,
  getCapacity,
  delCapacity,
  addCapacity,
  updateCapacity,
  exportCapacity
} from "@/api/reservior/capacity";
import { treeselect } from "@/api/system/org";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getUserProfile} from "@/api/system/user";

export default {
  name: "Capacity",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 组织树选项
      orgOptions: undefined,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库容表格数据
      capacityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      loginUser:null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        capacity: null,
        waterLevel: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        waterLevel:[
          { required: true, message: "水位不能为空", trigger: "blur" }
        ],
        capacity:[
          { required: true, message: "库容不能为空", trigger: "blur" }
        ],
        orgId:[
          { required: true, message: "水库不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getLoginUser();
  },
  methods: {
    /** 查询库容列表 */
    getList() {
      this.loading = true;
      listCapacity(this.queryParams).then(response => {
        this.capacityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getLoginUser(){
      getUserProfile().then(response => {
        this.loginUser = response.data;
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
        capacity: null,
        waterLevel: null,
        orgId: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询组织下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.orgOptions = response.data;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.orgId=this.loginUser.orgId;
      this.open = true;
      this.title = "添加库容";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCapacity(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库容";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCapacity(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCapacity(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除库容?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delCapacity(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有库容数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportCapacity(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

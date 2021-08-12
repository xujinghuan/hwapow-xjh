<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
        <el-form-item label="工地名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入工地名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工地位置" prop="address">
          <el-input
            v-model="queryParams.address"
            placeholder="请输入工地位置"
            clearable
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
        v-hasPermi="['worksite:worksite:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['worksite:worksite:edit']"
      >修改</el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['worksite:worksite:remove']"
      >删除</el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['worksite:worksite:export']"
      >导出</el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="worksiteList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
          <template scope="scope">
            <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="工地名称" align="center" prop="name"  width="250"/>
        <el-table-column label="工地位置" align="left" prop="address" width="250"/>
        <el-table-column label="工地单位" align="center" prop="serviceOrg" />
        <el-table-column label="开工时间" align="center" prop="startTime" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="预计结束时间" align="center" prop="expectEndTime" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.expectEndTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="实际结束时间" align="center" prop="actualEndTime" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.actualEndTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['worksite:worksite:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['worksite:worksite:remove']"
            >删除</el-button>
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
    <!-- 添加或修改工地对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="工地名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入工地名称" />
            </el-form-item>
            <el-form-item label="工地位置" prop="address">
              <el-input v-model="form.address" placeholder="请输入工地位置" />
            </el-form-item>
            <el-form-item label="工地内容">
              <editor v-model="form.content" :min-height="192"/>
            </el-form-item>
            <el-form-item label="工地单位" prop="serviceOrg">
              <el-input v-model="form.serviceOrg" placeholder="请输入工地单位" />
            </el-form-item>
            <el-form-item label="开工时间" prop="startTime">
              <el-date-picker clearable size="small"
                              v-model="form.startTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择开工时间">
              </el-date-picker>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item class="double-label-font" label="预计结束时间" prop="expectEndTime">
                  <el-date-picker clearable size="small"
                                  v-model="form.expectEndTime"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择预计结束时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="double-label-font" label="实际结束时间" prop="actualEndTime">
                  <el-date-picker clearable size="small"
                                  v-model="form.actualEndTime"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择实际结束时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import { listWorksite, getWorksite, delWorksite, addWorksite, updateWorksite, exportWorksite } from "@/api/worksite/worksite";
import Editor from '@/components/Editor';

export default {
  name: "Worksite",
  components: {
    Editor,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工地表格数据
      worksiteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        address: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "工地名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工地列表 */
    getList() {
      this.loading = true;
      listWorksite(this.queryParams).then(response => {
        this.worksiteList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        name: null,
        address: null,
        content: null,
        serviceOrg: null,
        startTime: null,
        expectEndTime: null,
        actualEndTime: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.names = selection.map(item => item.name)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工地";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWorksite(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工地";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.name=this.form.name.trim();
          if (this.form.id != null) {
            updateWorksite(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorksite(this.form).then(response => {
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
      const names = row.name || this.names;
      this.$confirm('确认删除工地"' + names + '"?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delWorksite(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工地数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportWorksite(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

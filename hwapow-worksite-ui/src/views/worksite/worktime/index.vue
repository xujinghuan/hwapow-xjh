<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
        <el-form-item label="工人" prop="worker">
          <treeselect style="width: 200px" class="vue-treeselect-mini" v-model="queryParams.worker" :options="workerOptions" :show-count="true" placeholder="请选择工人" />
        </el-form-item>
        <el-form-item label="所在工地" prop="worksite">
          <treeselect style="width: 300px" class="vue-treeselect-mini" v-model="queryParams.worksite" :options="worksiteOptions" :show-count="true" placeholder="请选择工地" />
        </el-form-item>
        <el-form-item label="工作日期" prop="workDate">
          <el-date-picker clearable
                          v-model="queryParams.workDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择工作日期">
          </el-date-picker>
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
        v-hasPermi="['worksite:worktime:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['worksite:worktime:edit']"
      >修改</el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['worksite:worktime:export']"
      >导出</el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="worktimeList" @selection-change="handleSelectionChange">
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
        <el-table-column label="工作日期" align="center" prop="workDate" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.workDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="工人" align="center" prop="workerName" width="100"/>
        <el-table-column label="所在工地" align="center" prop="worksiteName" width="300"/>
        <el-table-column label="日工资" align="center" prop="basicWage" width="80"/>
        <el-table-column label="工时" align="center" prop="taskTime" width="80"/>
        <el-table-column label="当天工资(元)" align="center" prop="salage" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.basicWage*scope.row.taskTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="工作内容" align="center" prop="content" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['worksite:worktime:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['worksite:worktime:remove']"
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
    <!-- 添加或修改工时记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="工作日期" prop="workDate">
              <el-date-picker clearable size="small"
                              v-model="form.workDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择工作日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="工人" prop="worker">
              <treeselect style="width: 200px" class="vue-treeselect-mini" v-model="form.worker" :options="workerOptions" :show-count="true" placeholder="请选择工人" />
            </el-form-item>
            <el-form-item label="所在工地" prop="worksite">
              <treeselect style="width: 300px" class="vue-treeselect-mini" v-model="form.worksite" :options="worksiteOptions" :show-count="true" placeholder="请选择工地" />
            </el-form-item>
            <el-row>
              <el-col :span="8">
                <el-form-item label="工时" prop="taskTime">
                  <el-input-number min = 0 max = 3 v-model="form.taskTime" placeholder="请输入工时" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="日工资" prop="basicWage">
                  <el-input-number min=10 v-model="form.basicWage" placeholder="请输入基本工资" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="当天工资">
                  {{form.taskTime*form.basicWage}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="工作内容">
              <el-input v-model="form.content" type="textarea"/>
            </el-form-item>
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
import { listWorktime, getWorktime, delWorktime, addWorktime, updateWorktime, exportWorktime } from "@/api/worksite/worktime";
import Editor from '@/components/Editor';
import {  workerTreeselect } from '@/api/worksite/worker'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { worksiteTreeselect } from '@/api/worksite/worksite'

export default {
  name: "Worktime",
  components: {
    Editor,Treeselect
  },
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工时记录表格数据
      worktimeList: [],
      //工人选项
      workerOptions:undefined,
      //工地选项
      worksiteOptions:undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        worker: null,
        worksite: null,
        basicWage: null,
        workDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        worker: [
          { required: true, message: "工人不能为空", trigger: "blur" }
        ],
        workDate: [
          { required: true, message: "工作日期不能为空", trigger: "blur" }
        ],
        worksite: [
          { required: true, message: "所在工地不能为空", trigger: "blur" }
        ],
        taskTime: [
          { required: true, message: "当天工时不能为空", trigger: "blur" }
        ],
        basicWage: [
          { required: true, message: "当天基本工资不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询工时记录列表 */
    getList() {
      this.loading = true;
      listWorktime(this.queryParams).then(response => {
        this.worktimeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 查询人员和工地下拉树结构 */
    getTreeselect() {
      workerTreeselect().then(response => {
        this.workerOptions = response.data;
      });
      worksiteTreeselect().then(response => {
        this.worksiteOptions = response.data;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        worker: null,
        worksite: null,
        basicWage: null,
        taskTime: null,
        content: null,
        workDate: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.title = "添加工时记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids
      getWorktime(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工时记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWorktime(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorktime(this.form).then(response => {
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
      this.$confirm('是否确认删除工时记录编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delWorktime(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工时记录数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportWorktime(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

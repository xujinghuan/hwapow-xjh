<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
        <el-form-item label="工人" prop="worker">
          <treeselect style="width: 200px" class="vue-treeselect-mini" v-model="queryParams.worker" :options="workerOptions" :show-count="true" placeholder="请选择工人" />
        </el-form-item>
        <el-form-item label="发放日期" prop="payDate">
          <el-date-picker clearable
                          v-model="queryParams.payDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择发放日期">
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
        v-hasPermi="['worksite:paySalary:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['worksite:paySalary:edit']"
      >修改</el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['worksite:paySalary:remove']"
      >删除</el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['worksite:paySalary:export']"
      >导出</el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="paySalaryList" @selection-change="handleSelectionChange">
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
        <el-table-column label="工人" align="center" prop="workerName" width="100"/>
        <el-table-column label="工资" align="center" prop="salary" width="60"/>
        <el-table-column label="发放日期" align="center" prop="payDate" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.payDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="发放方式" align="center" prop="payWay" :formatter="payWayFormat" width="100"/>
        <el-table-column label="理由" align="left" prop="reason"/>
        <el-table-column label="操作" width="130" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['worksite:paySalary:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['worksite:paySalary:remove']"
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
    <!-- 添加或修改工资发放对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="工人" prop="worker">
              <treeselect style="width: 200px" class="vue-treeselect-mini" v-model="form.worker" :options="workerOptions" :show-count="true" placeholder="请选择工人" />
            </el-form-item>
            <el-form-item label="工资" prop="salary">
              <el-input-number min="0" v-model="form.salary" placeholder="请输入工资" />
            </el-form-item>
            <el-form-item label="发放方式" prop="payWay">
              <el-select v-model="form.payWay" placeholder="请选择发放方式">
                <el-option
                  v-for="dict in payWayOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="发放日期" prop="payDate">
              <el-date-picker clearable size="small"
                              v-model="form.payDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择发放日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="理由" prop="reason">
              <el-input v-model="form.reason" type="textarea" placeholder="请输入内容" />
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
import { listPaySalary, getPaySalary, delPaySalary, addPaySalary, updatePaySalary, exportPaySalary } from "@/api/worksite/paySalary";
import {  workerTreeselect } from '@/api/worksite/worker'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "PaySalary",
  components: {
    Treeselect
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
      // 工资发放表格数据
      paySalaryList: [],
      // 字典
      payWayOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //工人选项
      workerOptions:undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        worker: null,
        payDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        worker: [
          { required: true, message: "工人不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("pay_way").then(response => {
      this.payWayOptions = response.data;
    });
    this.getTreeselect();
  },
  methods: {
    /** 查询工资发放列表 */
    getList() {
      this.loading = true;
      listPaySalary(this.queryParams).then(response => {
        this.paySalaryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询人员下拉树结构 */
    getTreeselect() {
      workerTreeselect().then(response => {
        this.workerOptions = response.data;
      });
    },
    // 字典翻译
    payWayFormat(row, column) {
      return this.selectDictLabel(this.payWayOptions, row.payWay);
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
        worker: null,
        reason: null,
        salary: null,
        payDate: null,
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
      this.title = "添加工资发放";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids
      getPaySalary(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工资发放";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaySalary(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaySalary(this.form).then(response => {
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
      const names=row.workerName+" "+row.payDate+"发放";
      this.$confirm('确认删除给' + names + '的'+row.salary+'工资?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delPaySalary(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工资发放数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportPaySalary(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

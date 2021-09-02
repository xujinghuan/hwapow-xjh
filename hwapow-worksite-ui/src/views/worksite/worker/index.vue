<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form :model="queryParams" class="searchForm" size="mini" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="工人名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入工人名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idcard">
          <el-input
            v-model="queryParams.idcard"
            placeholder="请输入身份证号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker clearable
                          v-model="queryParams.birthday"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工种" prop="workType">
          <el-select v-model="queryParams.workType" placeholder="请选择工种" clearable >
            <el-option
              v-for="dict in workTypeOptions"
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
        v-hasPermi="['worksite:worker:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['worksite:worker:edit']"
      >修改</el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['worksite:worker:remove']"
      >删除</el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['worksite:worker:export']"
      >导出</el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="workerList" @selection-change="handleSelectionChange">
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
        <el-table-column label="工人名称" align="center" prop="name" />
        <el-table-column label="工人昵称" align="center" prop="nickname" />
        <el-table-column label="身份证号" align="center" prop="idcard" width="180"/>
        <el-table-column label="电话号码" align="center" prop="phone" />
        <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormat" />
        <el-table-column label="出生日期" align="center" prop="birthday" width="100">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="工种" align="center" prop="workType" :formatter="workTypeFormat" />
        <el-table-column label="状态" align="center" prop="status" >
          <template slot-scope="scope">
            <span>{{ scope.row.status==0?"启用":"停用" }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['worksite:worker:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['worksite:worker:remove']"
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

    <!-- 添加或修改工人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px"  append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form  size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="工人名称" prop="name">
                  <el-input v-model="form.name" placeholder="请输入工人名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工人昵称" prop="nickname">
                  <el-input v-model="form.nickname" placeholder="请输入工人昵称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="身份证号" prop="idcard">
                  <el-input v-model="form.idcard" placeholder="请输入身份证号" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="电话号码" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入电话号码" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="性别" prop="sex">
                  <el-select v-model="form.sex" placeholder="请选择性别">
                    <el-option
                      v-for="dict in sexOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出生日期" prop="birthday">
                  <el-date-picker clearable
                                  v-model="form.birthday"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择出生日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="住址" prop="address">
                  <el-input v-model="form.address" placeholder="请输入住址" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="籍贯" prop="nativePlace">
                  <el-input v-model="form.nativePlace" placeholder="请输入籍贯" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="工种" prop="workType">
                  <el-select v-model="form.workType" placeholder="请选择工种">
                    <el-option
                      v-for="dict in workTypeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="double-label-font" label="银行账户名称" prop="accountName">
                  <el-input v-model="form.accountName" placeholder="请输入银行账户名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="银行账户" prop="account">
                  <el-input v-model="form.account" placeholder="请输入银行账户" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="开户行" prop="bankName">
                  <el-input v-model="form.bankName" placeholder="请输入开户行" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio label="0" value="0">启用</el-radio>
                <el-radio label="1" value="1">停用</el-radio>
              </el-radio-group>
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
import { listWorker, getWorker, delWorker, addWorker, updateWorker, exportWorker } from "@/api/worksite/worker";

export default {
  name: "Worker",
  components: {
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
      // 工人表格数据
      workerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      sexOptions: [],
      // 工种字典
      workTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        nickname: null,
        idcard: null,
        birthday: null,
        workType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "工人名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("work_type").then(response => {
      this.workTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询工人列表 */
    getList() {
      this.loading = true;
      listWorker(this.queryParams).then(response => {
        this.workerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 工种字典翻译
    workTypeFormat(row, column) {
      return this.selectDictLabel(this.workTypeOptions, row.workType);
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
        nickname: null,
        idcard: null,
        phone: null,
        sex: null,
        birthday: null,
        address: null,
        nativePlace: null,
        workType: null,
        accountName: null,
        account: null,
        bankName: null,
        status: "0",
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
      this.names= selection.map(item => item.name)
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工人";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWorker(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工人";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.name=this.form.name.trim();
          this.form.idcard=this.form.idcard!=null?this.form.idcard.trim():null;
          if (this.form.id != null) {
            updateWorker(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorker(this.form).then(response => {
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
      this.$confirm('确认删除工人"' + names + '"?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delWorker(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工人数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportWorker(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

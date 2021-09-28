<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="年份" prop="getYear">
          <el-date-picker
            v-model="queryParams.params.getYear"
            type="year"
            value-format="yyyy"
            placeholder="选择年份" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="月份" prop="getMonth">
          <el-select v-model="queryParams.params.getMonth" placeholder="请选择月份" clearable>
            <el-option
              v-for="item in monthOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="getDay">
          <el-select v-model="queryParams.params.getDay" placeholder="请选择日期" clearable>
            <el-option
              v-for="item in dayOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="断面" prop="sectionId">
          <el-select v-model="queryParams.sectionId" placeholder="请选择所属断面" clearable>
            <el-option
              v-for="dict in sectionOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备" prop="senorId">
          <el-select v-model="queryParams.senorId" placeholder="请选择所属断面" clearable>
            <el-option
              v-for="dict in senorOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <div class="toolbar">
      <!--<el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['reservior:data:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['reservior:data:edit']"
      >修改</el-button>-->
      <el-button
         type="danger"
         plain
         icon="el-icon-delete"
         size="mini"
         :disabled="multiple"
         @click="handleDelete"
         v-hasPermi="['reservior:data:remove']"
       >删除
       </el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['reservior:data:export']"
      >导出
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
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
        <el-table-column label="水库" align="center" prop="orgName"/>
        <el-table-column label="断面" align="center" prop="sectionName"/>
        <el-table-column label="设备" align="center" prop="senorName"/>
        <el-table-column label="数据获取时间" align="center" prop="getTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.getTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="返回指令" align="center" prop="backInstruction"/>
        <el-table-column label="原始数据" align="center" prop="rawData"/>
        <el-table-column label="数据" align="center" prop="data"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <!--<el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['reservior:data:edit']"
            >修改
            </el-button>-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['reservior:data:remove']"
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
    <!-- 添加或修改传感器监测数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {listData, getData, delData, addData, updateData, exportData} from "@/api/reservior/data";
import {listSection} from "@/api/reservior/section";
import {listSenor} from "@/api/reservior/senor";

export default {
  name: "MinorData",
  components: {},
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
      // 传感器监测数据表格数据
      dataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        params:{
          getYear: null,
          getMonth: null,
          getDay: null
        },
        sectionId:null,
        senorId:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      sectionOptions:[],//断面集合
      senorOptions:[],//设备集合
      monthOptions: ["01","02","03","04","05","06","07","08","09","10","11","12"],
      dayOptions: ["01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
    };
  },
  created() {
    this.getList();
    this.initQuery();
  },
  methods: {
    /** 查询传感器监测数据列表 */
    getList() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.dataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    initQuery(){
      listSection().then(response => {
        this.sectionOptions = response.rows;
      });
      listSenor().then(response => {
        this.senorOptions = response.rows;
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
        senorId: null,
        getTime: null,
        backInstruction: null,
        rawData: null,
        data: null,
        sectionId: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加传感器监测数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getData(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改传感器监测数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateData(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addData(this.form).then(response => {
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
      this.$confirm('是否确认删除传感器监测数据编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delData(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出传感器监测数据数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportData(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

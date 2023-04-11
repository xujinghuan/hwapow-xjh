<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
        <el-form-item label="办理的事情" prop="toDoThing">
          <el-input
            v-model="queryParams.toDoThing"
            placeholder="请输入办理的事情"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="办理的时间">
          <el-date-picker
            v-model="daterangeToDoTime"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="完成时间">
          <el-date-picker
            v-model="daterangeDoneTime"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
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
        v-hasPermi="['schedule:toDoThing:add']"
      >新增</el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['schedule:newEmpToDo:edit']"
      >修改</el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['schedule:toDoThing:remove']"
      >删除</el-button>
      <!--<el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['schedule:toDoThing:export']"
      >导出</el-button>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-menu class="el-menu-demo hwapow-table-menu" mode="horizontal" default-active="1"  @select="handleMenuSelect">
        <el-menu-item index="1" >未完成</el-menu-item>
        <el-menu-item index="2" >已完成</el-menu-item>
        <el-menu-item index="3" >已关闭</el-menu-item>
      </el-menu>
      <el-table v-loading="loading" :data="toDoThingList" @selection-change="handleSelectionChange">
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
        <el-table-column label="办理的事情" header-align="center" align="left" prop="toDoThing">
          <template slot-scope="scope">
            <span @click="openViewDialog(scope.row)">{{ scope.row.toDoThing }}</span>
          </template>
        </el-table-column>
        <el-table-column label="办理的时间" align="center" prop="toDoTime" width="150">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.toDoTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="!handleColumeShow" label="完成时间" align="center" prop="doneTime" width="150">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.doneTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="isDone" width="80" >
          <template slot-scope="scope">
            <span>{{ scope.row.isClose==1?"已关闭":(scope.row.isClose==1?"已完成":"待办") }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="handleColumeShow" label="操作" align="center" class-name="small-padding fixed-width" width="180">
          <template slot-scope="scope">
            <!--未完成并且未关闭的可以有完成按钮-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDone(scope.row)"
              v-if="scope.row.isDone==0&&scope.row.isClose==0"
            >完成</el-button>
            <!--未完成并且未关闭的可以有完成按钮-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-sort"
              @click="handleTodoTime(scope.row)"
              v-if="scope.row.isDone==0&&scope.row.isClose==0"
            >顺延</el-button>
            <!--未完成并且未关闭的可以有关闭按钮-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleClose(scope.row)"
              v-if="scope.row.isClose==0&&scope.row.isDone==0"
            >关闭</el-button>
            <!--关闭的可以有打开按钮-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-plus"
              @click="handleOpen(scope.row)"
              v-if="scope.row.isClose==1"
            >打开</el-button>
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
    <!-- 添加或修改应办事情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="450px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="办理时间" prop="toDoTime">
              <el-date-picker clearable size="small"
                              v-model="form.toDoTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择办理的时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="办理事情" prop="toDoThing">
              <el-input type="textarea" v-model="form.toDoThing" placeholder="请输入办理的事情" :rows="8" maxlength="300"/>
            </el-form-item>
            <el-form-item v-if="form.id!=null" label="完成情况" prop="doneThing">
              <el-input v-model="form.doneThing" placeholder="请输入完成情况" />
            </el-form-item>
            <el-form-item v-if="form.id!=null" label="完成时间" prop="doneTime">
              <el-date-picker clearable size="small"
                              v-model="form.doneTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择完成时间">
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-main>
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import { listToDoThing, getToDoThing, delToDoThing, addToDoThing, updateToDoThing, exportToDoThing } from "@/api/schedule/toDoThing";

export default {
  name: "ToDoThing",
  components: {
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
      //是否显示操作列
      handleColumeShow:true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 应办事情表格数据
      toDoThingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 办理的时间时间范围
      daterangeToDoTime: [],
      // 完成时间时间范围
      daterangeDoneTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        toDoThing: null,
        toDoTime: null,
        employeeId: null,
        transactor: null,
        doneTime: null,
        isClose: 0,
        isDone: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "记录人不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询应办事情列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeToDoTime && '' != this.daterangeToDoTime) {
        this.queryParams.params["beginToDoTime"] = this.daterangeToDoTime[0];
        this.queryParams.params["endToDoTime"] = this.daterangeToDoTime[1];
      }
      if (null != this.daterangeDoneTime && '' != this.daterangeDoneTime) {
        this.queryParams.params["beginDoneTime"] = this.daterangeDoneTime[0];
        this.queryParams.params["endDoneTime"] = this.daterangeDoneTime[1];
      }
      listToDoThing(this.queryParams).then(response => {
        this.toDoThingList = response.rows;
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
        toDoThing: null,
        toDoTime: null,
        employeeId: null,
        transactor: null,
        doneThing: null,
        doneTime: null,
        isClose: "0",
        isDone: "0",
        userId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.daterangeToDoTime = [];
      this.daterangeDoneTime = [];
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
      this.open = true;
      this.title = "添加应办事情";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getToDoThing(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改应办事情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateToDoThing(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addToDoThing(this.form).then(response => {
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
      this.$confirm('是否确认删除应办事情编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delToDoThing(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有应办事情数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportToDoThing(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    //菜单选项
    handleMenuSelect(key){
      if(key==1){//未完成
        this.queryParams.isClose=0;
        this.queryParams.isDone=0;
        this.handleColumeShow=true;
      }else if(key==2){//已完成
        this.queryParams.isClose=null;
        this.queryParams.isDone=1;
        this.handleColumeShow=false;
      }else if(key==3){
        this.queryParams.isClose=1;
        this.queryParams.isDone=null;
        this.handleColumeShow=true;
      }
      this.getList();
    },
    //完成
    handleDone(row){
      if(row.isClose==0){//未关闭状态
        this.$prompt('完成情况', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({ value }) => {
          row.isDone=1;
          row.doneThing=value;
          row.doneTime=new Date();
          updateToDoThing(row).then(response=>{
            this.msgSuccess("已关闭！")
            this.getList();
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消操作'
          });
        });
      }else{
        this.msgError("此事项已关闭，不可完成！")
      }
    },
    //关闭
    handleClose(row){
      if(row.isDone==0){//未完成状态
        row.isClose=1;
        updateToDoThing(row).then(response=>{
          this.msgSuccess("已关闭！")
          this.getList();
        });
      }else{
        this.msgError("此事项已完成，不可关闭！")
      }
    },
    //打开
    handleOpen(row){
      row.isClose=0;
      updateToDoThing(row).then(response=>{
        this.msgSuccess("已打开！")
        this.getList();
      });
    },
    //顺延时间
    handleTodoTime(row){
      if(row.isClose==0){//未关闭状态
        console.log(row.toDoTime);
        this.$prompt('修改办理时间至', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputType:'date',
          inputValue:row.toDoTime
        }).then(({ value }) => {
          console.log(value);
          if(value){
            row.toDoTime=value;
            updateToDoThing(row).then(response=>{
              this.msgSuccess("已修改时间！")
              this.getList();
            });
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消操作'
          });
        });
      }else{
        this.msgError("此事项已关闭，不可完成！")
      }
    }
  }
};
</script>

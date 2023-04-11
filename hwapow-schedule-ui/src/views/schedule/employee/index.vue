<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="员工编码" prop="code">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入员工编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="员工名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入员工名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="所属部门" prop="orgId">
          <treeselect style="width: 200px" class="vue-treeselect-mini" v-model="queryParams.orgId" :options="orgOptions"
                      :normalizer="normalizer" placeholder="选择所属部门" @keyup.enter.native="handleQuery"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="启用" value="1"/>
            <el-option label="停用" value="2"/>
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
        v-hasPermi="['schedule:employee:add']"
      >新增
      </el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['schedule:employee:edit']"
      >修改
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['schedule:employee:remove']"
      >删除
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="openEntryThing"
        v-hasPermi="['schedule:employee:remove']"
      >生成员工入职事宜
      </el-button>
      <!-- <el-button
         type="warning"
         plain
         icon="el-icon-download"
         size="mini"
         @click="handleExport"
         v-hasPermi="['schedule:employee:export']"
       >导出</el-button>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
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
        <el-table-column label="员工编码" align="center" prop="code"/>
        <el-table-column label="员工名称" align="center" prop="name"/>
        <el-table-column label="所属部门" align="center" prop="orgId"/>
        <el-table-column label="身份证号" align="center" prop="idCard"/>
        <el-table-column label="入职日期" align="center" prop="entryDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="员工生日" align="center" prop="birthday" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="身份证有效截止时间" align="center" prop="cardExpiryDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.cardExpiryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <span>{{ scope.row.status == 2 ? "停用" : "启用" }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['schedule:employee:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['schedule:employee:remove']"
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
    <!-- 添加或修改员工管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="员工编码" prop="code">
                  <el-input v-model="form.code" placeholder="请输入员工编码"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="员工名称" prop="name">
                  <el-input v-model="form.name" placeholder="请输入员工名称"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="所属部门" prop="orgId">
                  <treeselect class="vue-treeselect-mini" v-model="form.orgId" :options="orgOptions"
                              :normalizer="normalizer" placeholder="选择所属部门"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="身份证号" prop="idCard">
                  <el-input v-model="form.idCard" placeholder="请输入身份证号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="员工生日" prop="birthday">
                  <el-date-picker clearable size="small"
                                  v-model="form.birthday"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择员工生日">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="double-label-font" label="身份证有效截止日" prop="cardExpiryDate">
                  <el-date-picker clearable size="small"
                                  v-model="form.cardExpiryDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择身份证有效截止时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="入职日期" prop="entryDate">
                  <el-date-picker clearable size="small"
                                  v-model="form.entryDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择入职日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态">
                  <el-radio-group v-model="form.status">
                    <el-radio label="1">正常</el-radio>
                    <el-radio label="2">停用</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-main>
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
      </el-container>
    </el-dialog>
    <!-- 生成员工入职事宜对话框 -->
    <el-dialog title="生成员工入职事宜" :visible.sync="entryThingOpen" width="750px" append-to-body :show-close=false :close-on-click-modal="false">
      <el-container class="fullContainer" direction="vertical">
        <el-main>
          <span style="width: 100%;color: red;font-size: 20px;padding: 20px;">{{ entryThingTip }}</span>
          <el-table
            :data="entryThingData"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="姓名"
              width="100"
              align="center">
            </el-table-column>
            <el-table-column
              prop="entrydate"
              label="入职日期"
              width="100"
              align="center">
            </el-table-column>
            <el-table-column
              prop="doDate"
              label="办理日期"
              width="100"
              align="center">
            </el-table-column>
            <el-table-column
              prop="todoThing"
              label="应办事宜"
              align="left"
              header-align="center">
            </el-table-column>
          </el-table>
        </el-main>
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitEntryThing">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancelEntryThing">取 消</el-button>
        </div>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {
  listEmployee,
  getEmployee,
  delEmployee,
  addEmployee,
  updateEmployee,
  exportEmployee, createEnteyThing
} from "@/api/schedule/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listOrgNoDataScode} from "@/api/system/org";
import {validIdentityCode} from "@/utils/validate";
import {listNewEmpToDo} from "@/api/schedule/newEmpToDo";
import {dateAddDays} from "@/utils/hwapowUtils"
import {listToDoThing} from "@/api/schedule/toDoThing";

export default {
  name: "Employee",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 员工管理表格数据
      employeeList: [],
      //入职事宜
      entryThingData:[],
      //生成提示
      entryThingTip:"",
      // 组织树选项
      orgOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是都显示生成入职事宜弹出框
      entryThingOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        orgId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          {required: true, message: "员工编码不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "员工姓名不能为空", trigger: "blur"}
        ],
        orgId: [
          {required: true, message: "所属部门不能为空", trigger: "blur"}
        ],
        idCard: [
          {required: true, message: "身份证号不能为空", trigger: "blur"}
        ],
        birthday: [
          {required: true, message: "生日不能为空", trigger: "blur"}
        ],
        cardExpiryDate: [
          {required: true, message: "身份证有效期不能为空", trigger: "blur"}
        ],
        entryDate: [
          {required: true, message: "入职日期不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getOrgOptions();
  },
  methods: {
    /** 查询员工管理列表 */
    getList() {
      this.loading = true;
      listEmployee(this.queryParams).then(response => {
        this.employeeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取组织机构列表
    getOrgOptions() {
      listOrgNoDataScode().then(response => {
        this.orgOptions = this.handleTree(response.data, "orgId");
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
        orgId: null,
        idCard: null,
        entryDate: null,
        birthday: null,
        cardExpiryDate: null,
        status: "1",
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.names = selection.map(item => item.name)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEmployee(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!validIdentityCode(this.form.idCard)) {
            this.msgError("身份证号不正确");
            return;
          }
          if (this.form.id != null) {
            updateEmployee(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmployee(this.form).then(response => {
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
      this.$confirm('是否确认删除员工为"' + names + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delEmployee(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有员工管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportEmployee(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    //生成员工入职事宜弹框
    openEntryThing() {
      const id = this.ids;
      this.entryThingData=[];
      getEmployee(id).then(response => {
        let employee = response.data;
        if(employee){
          //先查询是否已经生成过相关事宜
          listToDoThing({employeeId:employee.id}).then(response=>{
            if(response.total>0){
              this.entryThingTip="此员工入职事宜已生成过！！！！点击“确认”，以下待办事项将覆盖此员工原先待办事项";
            }else{
              this.entryThingTip="即将生成以下待办事项，请确认！";
            }
            listNewEmpToDo().then(response => {
              for (var i in response.rows){
                this.entryThingData.push({
                  name:employee.name,
                  entrydate:employee.entryDate,
                  doDate:dateAddDays(employee.entryDate,response.rows[i].entryDayNum),
                  todoThing:employee.name+"-"+response.rows[i].toDoThing
                });
              }
              this.entryThingOpen = true;
            })
          });

        }
      });
    },
    //提交员工入职事宜
    submitEntryThing(){
      const id = this.ids;
      createEnteyThing(id).then(response=>{
        this.msgSuccess("新员工入职事宜创建成功！")
      })
      this.entryThingData=[];
      this.entryThingOpen = false;
    },
    //取消员工入职事宜
    cancelEntryThing(){
      this.entryThingData=[];
      this.entryThingOpen = false;
    }
  }
};
</script>

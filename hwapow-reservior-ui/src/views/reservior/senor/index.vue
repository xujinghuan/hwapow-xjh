<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="设备编码" prop="code">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入设备编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="设备名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入设备名称"
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
        v-hasPermi="['reservior:senor:add']"
      >新增
      </el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['reservior:senor:edit']"
      >修改
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['reservior:senor:remove']"
      >删除
      </el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['reservior:senor:export']"
      >导出
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="senorList" @selection-change="handleSelectionChange">
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
        <el-table-column label="设备编码" align="center" prop="code" width="100"/>
        <el-table-column label="设备名称" align="center" prop="name" width="130"/>
        <el-table-column label="所属断面" align="center" prop="sectionName" width="130"/>
        <el-table-column label="所属水库" align="center" prop="orgName" width="130"/>
        <el-table-column label="零点高程（米）" align="center" prop="zeroElevation" width="150"/>
        <el-table-column label="管口高程（米）" align="center" prop="nozzleElevation" width="150"/>
        <el-table-column label="取数指令" align="center" prop="getInstruction" width="300"/>
        <el-table-column label="顺序号" align="center" prop="sort" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['reservior:senor:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['reservior:senor:remove']"
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
    <!-- 添加或修改设备管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" height="200px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="120px">
            <el-form-item label="设备编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入设备编码"/>
            </el-form-item>
            <el-form-item label="设备名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入设备名称"/>
            </el-form-item>
            <el-form-item label="所属断面" prop="sectionId">
              <el-select v-model="form.sectionId" placeholder="请输入所属断面">
                <el-option
                  v-for="dict in sectionOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择所属类型">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="警戒水位最大值" prop="warnMax">
              <el-input v-model="form.warnMax" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="警戒水位最大值"/>
            </el-form-item>
            <el-form-item label="警戒水位最小值" prop="warnMin">
              <el-input v-model="form.warnMin" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="警戒水位最小值"/>
            </el-form-item>
            <el-form-item label="返回数据最大值" prop="backDataMax">
              <el-input v-model="form.backDataMax" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="返回数据最大值（以原始数据位标准）"/>
            </el-form-item>
            <el-form-item label="返回数据单位" prop="backDataUnit">
              <el-select v-model="form.backDataUnit" placeholder="请选择返回数据单位">
                <el-option
                  v-for="dict in backDataUnitOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="form.type==0" label="对应坝基设备" prop="matchSenorId">
              <el-select v-model="form.matchSenorId" placeholder="请选择对应坝基设备">
                <el-option
                  v-for="dict in bjSenorOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="零点高程(米)" prop="zeroElevation">
              <el-input v-model="form.zeroElevation" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="请输入零点高程"/>
            </el-form-item>
            <el-form-item label="管口高程(米)" prop="nozzleElevation">
              <el-input v-model="form.nozzleElevation" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="请输入管口高程"/>
            </el-form-item>
            <el-form-item label="取数指令" prop="getInstruction">
              <el-input v-model="form.getInstruction" placeholder="请输入取数指令"/>
            </el-form-item>
            <el-form-item label="x轴百分比" prop="x">
              <el-input v-model="form.x" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="请输入以水库图左上角为原点，x轴距离原点的百分比"/>
            </el-form-item>
            <el-form-item label="y轴百分比" prop="y">
              <el-input v-model="form.y" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                        placeholder="请输入以水库图左上角为原点，y轴距离原点的百分比"/>
            </el-form-item>
            <el-form-item label="顺序号" prop="sort">
              <el-input v-model="form.sort" onkeyup="value=value.replace(/[^\.\d]/g,'')" placeholder="请输入顺序号"/>
            </el-form-item>
            <el-form-item class="double-label-font" label="返回指令设备标识" prop="backIdentification">
              <el-input v-model="form.backIdentification" placeholder="请输入返回指令设备标识，比如返回指令01代表设备T1-1"/>
            </el-form-item>
            <span style="color: red">返回指令设备标识开始下标，mysql中SubStr函数从1开始数,如D7 01 00 01 00 07 01 03 02 01 2C B8 09中标识是D7 01 00 01 00 07 01，则返回指令标识开始下标为1</span>
            <el-form-item class="double-label-font" label="返回指令设备标识开始下标" prop="backIdenIndexS">
              <el-input-number min = 1 v-model="form.backIdenIndexS" />
            </el-form-item>
            <span style="color: red">返回指令数据开始下标，java中substr函数从0开始数，截取不包含结束位，如如D7 01 00 01 00 07 01 03 02 01 2C B8 09中标识是01 2C，则返回指令数据开始下标为27,结束下标为32</span>
            <el-form-item class="double-label-font" label="返回指令数据开始下标" prop="backDataIndexS">
              <el-input-number min = 1 v-model="form.backDataIndexS" placeholder="请输入返回指令中数据开始下标" onkeyup="value=value.replace(/[^\.\d]/g,'')"/>
            </el-form-item>
            <el-form-item class="double-label-font" label="返回指令数据结束下标" prop="backDataIndexE">
              <el-input-number min = 1 v-model="form.backDataIndexE" placeholder="请输入返回指令中数据结束下标" onkeyup="value=value.replace(/[^\.\d]/g,'')"/>
            </el-form-item>
            <span style="color: red">sql中不可以存在delete和update，sql中{senorId}代表设备id，{rowData}代表原始数据，如select {rowData}+100 from res_senor where id={senorId}</span>
            <el-form-item class="double-label-font" label="返回数据计算公式（sql）" prop="backDataFormula">
              <el-input type="textarea" v-model="form.backDataFormula" placeholder="请输入返回数据计算公式（sql）"/>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="form.remark" placeholder="请输入备注"/>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {listSenor, getSenor, delSenor, addSenor, updateSenor, exportSenor} from "@/api/reservior/senor";
import {listSection} from "@/api/reservior/section";

export default {
  name: "Senor",
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
      sectionOptions: null,
      // 设备管理表格数据
      senorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 15,
        code: null,
        name: null,
      },
      // 表单参数
      form: {},
      typeOptions:[],
      backDataUnitOptions:[],
      bjSenorOptions:[],
      // 表单校验
      rules: {
        code: [
          {required: true, message: "编码不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "名称不能为空", trigger: "blur"}
        ],
        sectionId: [
          {required: true, message: "断面不能为空", trigger: "blur"}
        ],
        type:[
          {required: true, message: "类型不能为空", trigger: "blur"}
        ],
        backDataUnit:[
          {required: true, message: "返回数据单位不能为空", trigger: "blur"}
        ],
        sort:[
          {required: true, message: "请输入顺序号", trigger: "blur"}
        ],
        zeroElevation:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        nozzleElevation:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        getInstruction:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backIdentification:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backIdenIndexS:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backDataIndexS:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backDataIndexE:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backDataFormula:[
          {required: true, message: "请输入值", trigger: "blur"}
        ],
        backDataMax:[
          {required: true, message: "请输入值", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getSectionOptions();
    this.getDicts("senor_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("back_data_unit").then(response => {
      this.backDataUnitOptions = response.data;
    });
  },
  methods: {
    getSectionOptions() {
      listSection(this.queryParams).then(response => {
        this.sectionOptions = response.rows;
      });
    },
    getBjSenorOptions(){
      listSenor({type:1}).then(response => {
        this.bjSenorOptions = response.rows;
      });
    },
    /** 查询设备管理列表 */
    getList() {
      this.loading = true;
      listSenor(this.queryParams).then(response => {
        this.senorList = response.rows;
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
        code: null,
        name: null,
        sectionId: null,
        zeroElevation: null,
        nozzleElevation: null,
        getInstruction: null,
        x: null,
        y: null,
        sort: null,
        type:null,
        backDataUnit:null,
        matchSenorId:null,
        backIdentification: null,
        backIdenIndexS: null,
        backDataIndexS: null,
        backDataIndexE: null,
        backDataFormula: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        backDataMax:null,
        warnMin:null,
        warnMax:null
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
      this.getBjSenorOptions();
      this.open = true;
      this.title = "添加设备管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getBjSenorOptions();
      const id = row.id || this.ids
      getSenor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if(this.form.backDataFormula.toLowerCase().indexOf("delete")>=0||this.form.backDataFormula.toLowerCase().indexOf("update")>=0){
        this.msgError("计算公式中不可以存在update和delete");
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSenor(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSenor(this.form).then(response => {
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
      this.$confirm('是否确认删除设备管理编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSenor(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有设备管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSenor(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

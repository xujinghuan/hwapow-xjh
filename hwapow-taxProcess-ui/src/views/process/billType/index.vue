<template>
  <el-container class="fullContainer">
    <div class="toolbar">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['process:billType:add']"
      >新增</el-button>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="billTypeList">
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
        <el-table-column label="单据编码" align="center" prop="code" />
        <el-table-column label="单据名称" align="center" prop="name" />
        <el-table-column label="单据简码" align="center" prop="ruleCode" />
        <el-table-column label="文件号规则" align="center" prop="docNumRuleName" />
        <el-table-column label="使用流程" align="center" prop="processName" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['process:billType:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['process:billType:remove']"
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
    <!-- 添加或修改单据类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="450px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="单据编码" prop="code">
              <el-input v-model="form.code" placeholder="单据编码" :disabled="form.id?true:false"/>
            </el-form-item>
            <el-form-item label="单据名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入单据名称" />
            </el-form-item>
            <el-form-item label="单据简码" prop="ruleCode">
              <el-input v-model="form.ruleCode" placeholder="请输入单据简码，生成文件号使用" />
            </el-form-item>
            <el-form-item label="文件号规则" prop="docNumRuleId">
              <el-select v-model="form.docNumRuleId" placeholder="请选择文件号规则">
                <el-option
                  v-for="item in docNumRuleList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="使用流程" prop="processId">
              <el-select v-model="form.processId" placeholder="请选择流程">
                <el-option
                  v-for="item in processList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import { listBillType, getBillType, delBillType, addBillType, updateBillType, exportBillType } from "@/api/process/billType";
import { listDocNumRule } from '@/api/process/docNumRule'
import { listProcess } from '@/api/process/process'

export default {
  name: "BillType",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 单据类型表格数据
      billTypeList: [],
      //流程列表
      processList:[],
      //文件号规则列表
      docNumRuleList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        code: [
          { required: true, message: "编码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDocNumRuleList();
    this.getProcessList();
  },
  methods: {
    /** 查询单据类型列表 */
    getList() {
      this.loading = true;
      listBillType(this.queryParams).then(response => {
        this.billTypeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getDocNumRuleList(){
      listDocNumRule().then(response =>{
        this.docNumRuleList=response.rows;
      });
    },
    getProcessList(){
      listProcess().then(response =>{
        this.processList=response.rows;
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
        ruleCode: null,
        docNumRuleId: null,
        processId: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加单据类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getBillType(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单据类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBillType(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBillType(this.form).then(response => {
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
      const ids = row.id;
      const code=row.code;
      this.$confirm('是否确认删除单据类型编号为"' + code + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delBillType(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    }
  }
};
</script>

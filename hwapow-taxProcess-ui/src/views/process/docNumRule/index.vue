<template>
  <el-container class="fullContainer">
    <el-main  class="el-mian-left" style="width: 550px">
      <div class="toolbar">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['process:docNumRule:add']"
        >新增规则</el-button>
      </div>
      <el-main>
        <el-table v-loading="loading" :highlight-current-row="true" :data="docNumRuleList" @row-click="handleSelectionChange">
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center">
            <template scope="scope">
              <span>{{scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="规则编码" align="center" prop="code"  width="120"/>
          <el-table-column label="规则名称" align="center" prop="name"  width="120"/>
          <el-table-column label="规则" align="center" prop="rule"  width="150"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['process:docNumRule:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['process:docNumRule:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-main>
    <el-main >
      <doc-num :rule-id="selectRuleId"></doc-num>
    </el-main>
    <!-- 添加或修改文件号规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="规则名称" prop="name">
                  <el-input v-model="form.name" placeholder="请输入规则名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="规则编码" prop="code">
                  <el-input v-model="form.code" placeholder="请输入规则编码" :disabled="form.id?true:false" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <div style="padding-left: 91px;line-height: 12px;font-size: 12px;color: #ff0000;">{year}-年份 {type}-类型 {num}-顺序号</div>
                <el-form-item label="规则" prop="rule">
                  <el-input v-model="form.rule" placeholder="请输入规则" />
                </el-form-item>
              </el-col>
            </el-row>
            <div style="padding-left: 91px;line-height: 12px;font-size: 12px;color: #ff0000;">type参数使用'{billId}'代表单据Id,sql返回值必须是一个字符串，只有一个返回值</div>
            <el-form-item label="type取值语句Sql" prop="typeSql">
              <el-input v-model="form.typeSql" type="textarea" :autosize="{ minRows: 5, maxRows: 12 }" placeholder="请输入内容" />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
  <!--
页面：文件规则界面
作者：xjh
添加时间：2022-06-10
-->
</template>

<script>
import { listDocNumRule, getDocNumRule, delDocNumRule, addDocNumRule, updateDocNumRule } from "@/api/process/docNumRule";
import DocNum from '@/views/process/docNumRule/docNum'

export default {
  name: "DocNumRule",
  components: {
    DocNum
  },
  data() {
    return {
      //选择的规则Id
      selectRuleId:null,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 文件号规则表格数据
      docNumRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "规则名称不能为空", trigger: "blur" }
        ],
        code: [
          { required: true, message: "规则编码不能为空", trigger: "blur" }
        ],
        rule: [
          { required: true, message: "规则不能为空", trigger: "blur" }
        ],
        isType: [
          { required: true, message: "是否计算type不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件号规则列表 */
    getList() {
      this.loading = true;
      listDocNumRule(this.queryParams).then(response => {
        this.docNumRuleList = response.rows;
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
        code: null,
        rule: null,
        isType: "0",
        typeSql: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件号规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getDocNumRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件号规则";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDocNumRule(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDocNumRule(this.form).then(response => {
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
      const ids = row.id
      this.$confirm('是否确认删除文件号规则"' +row.name+'-'+ row.code + '"?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delDocNumRule(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    handleSelectionChange(row){
      this.selectRuleId=row.id;
    }
  }
};
</script>

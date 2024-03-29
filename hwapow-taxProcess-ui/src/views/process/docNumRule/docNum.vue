<template>
  <el-container class="fullContainer">
    <div class="toolbar">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['process:docNumRule:add']"
      >新增顺序号</el-button>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="docNumList">
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
          <template scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="所属规则" align="center" prop="ruleName" width="250"/>
        <el-table-column label="年份" align="center" prop="year" width="150"/>
        <el-table-column label="类型" align="center" prop="type" width="150"/>
        <el-table-column label="顺序号" align="center" prop="num" />
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
    <el-footer>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-footer>
    <!-- 添加或修改文件顺序号记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="450px" style="height: 500px;" append-to-body>
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="年份" prop="year">
              <el-date-picker
                v-model="form.year"
                type="year"
                value-format="yyyy"
                placeholder="选择年">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-input v-model="form.type" placeholder="请输入类型" />
            </el-form-item>
            <el-form-item label="顺序号" prop="num">
              <el-input-number v-model="form.num" controls-position="right" :min="0" :precision="0"></el-input-number>
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
页面：文件规则顺序号界面。用在index界面中
作者：xjh
添加时间：2022-06-15
-->
</template>

<script>
import { listDocNum, getDocNum, delDocNum, addDocNum, updateDocNum } from "@/api/process/docNum";

export default {
  name: "DocNum",
  components: {
  },
  props: {
    ruleId:null
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 文件顺序号记录表格数据
      docNumList: [],
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
        num: [
          { required: true, message: "顺序号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件顺序号记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.ruleId=this.ruleId;
      listDocNum(this.queryParams).then(response => {
        this.docNumList = response.rows;
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
        ruleId: null,
        year: null,
        type: null,
        num: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if(!this.ruleId){
        this.msgError("请选择左侧文件号规则！");
        return;
      }
      this.open = true;
      this.title = "添加文件顺序号记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getDocNum(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件顺序号记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDocNum(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if(!this.ruleId){
              this.msgError("请选择左侧文件号规则！");
              return;
            }
            this.form.ruleId=this.ruleId;
            addDocNum(this.form).then(response => {
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
      this.$confirm('是否确认删除此文件顺序号记录?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delDocNum(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    }
  },
  watch:{
    ruleId(){
      this.getList();
    }
  }
};
</script>

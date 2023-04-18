<template>
  <el-container class="fullContainer">
    <el-main class="el-mian-left" style="width: 500px">
      <div class="toolbar">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['process:process:add']"
        >新增流程</el-button>
      </div>
      <el-main>
        <el-table v-loading="loading" :highlight-current-row="true" :data="processList" @row-click="handleSelectionChange">
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center">
            <template scope="scope">
              <span>{{scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="流程编码" align="center" prop="code" width="100"/>
          <el-table-column label="流程名称" align="center" prop="name" width="150"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['process:process:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['process:process:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-main>
    <el-main>
         <node :process-id="selectProcessId"></node>
    </el-main>
    <!-- 添加或修改流程管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="350px" append-to-body :close-on-click-modal="false">
      <el-container  class="fullContainer" direction="vertical" style="height: 300px">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="流程编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入流程编码" />
            </el-form-item>
            <el-form-item label="流程名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入流程名称" />
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
import { listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/process/process";
import Node from '@/views/process/process/node'
import { approveBill, getApproveLog, getBtnName, initBill } from '@/api/process/billUtil'

export default {
  name: "Process",
  components: {
    Node
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
      // 流程管理表格数据
      processList: [],
      //选择的流程id
      selectProcessId:null,
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
  },
  methods: {
    /** 查询流程管理列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
      this.title = "添加流程管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProcess(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcess(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcess(this.form).then(response => {
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
      this.$confirm('是否确认删除流程管理编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delProcess(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    handleSelectionChange(row){
      this.selectProcessId=row.id;
    }
  }
};
</script>

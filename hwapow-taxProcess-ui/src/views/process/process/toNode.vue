<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
    </el-header>
    <div class="toolbar">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['process:process:add']"
      >节点流转</el-button>
    </div>
    <el-main>
      <el-table v-loading="loading" :data="toNodeList">
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
          <template scope="scope">
            <span>{{scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="主节点" align="center" prop="fromNodeName" />
        <el-table-column label="流转后节点" align="center" prop="toNodeName" />
        <el-table-column label="流转类型" align="center" prop="toType" >
          <template slot-scope="scope">
            <span>{{getToTypeValue(scope.row.toType)}}</span>
          </template>
        </el-table-column>
        <el-table-column label="排序" align="center" prop="sortIndex" />
        <el-table-column label="流转后业务状态" align="center" prop="billState" :formatter="billStateFormat" />
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
    <el-footer>
    </el-footer>
    <!-- 添加或修改流转节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body :close-on-click-modal="false">
      <el-container  class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="130px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="流转后节点" prop="toNodeId">
                  <el-select v-model="form.toNodeId" placeholder="请选择">
                    <el-option
                      v-for="item in nodeList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                      :disabled="item.id==fromNodeId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="流转类型" prop="toType">
                  <el-select v-model="form.toType" placeholder="请选择流转类型">
                    <el-option label="提交" value="0" />
                    <el-option label="通过" value="1" />
                    <el-option label="退回" value="2" />
                    <el-option label="驳回" value="3" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="流转后业务状态" prop="billState">
                  <el-select v-model="form.billState" placeholder="请选择">
                    <el-option
                      v-for="dict in billStateOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="排序" prop="sortIndex">
                  <el-input-number v-model="form.sortIndex" :precision="0" :step="1" :max="100"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>

            <div style="padding-left: 91px;line-height: 14px;font-size: 12px;color: #ff0000;">流转条件sql语句中不可以包含update和delete语句，参数使用'{billId}'代表单据Id，返回值必须为0或者1（1代表符合流转条件，0代表不符合流转条件）,按顺序检验流转条件，如果流转条件为空，默认流转到该节点</div>
            <el-form-item label="流转条件sql" prop="condition">
              <el-input v-model="form.condition" :autosize="{ minRows: 10}" type="textarea" placeholder="请输入内容" />
            </el-form-item>
            <div style="padding-left: 91px;line-height: 14px;font-size: 12px;color: #ff0000;">流转后执行的是后台的方法，此类必须为Service类，类名要包括类路径，如：com.hwapow.service.impl.XXX,方法写参数，默认参数为单据Id，后台写的方法中必须有一个Long参数</div>
            <el-row>
              <el-col :span="12">
                <el-form-item label="流转后执行：类名" prop="toAfter">
                  <el-input v-model="toAfter_class" type="text" placeholder="类名" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="方法名" prop="toAfter">
                  <el-input v-model="toAfter_method" type="text" placeholder="方法名" />
                </el-form-item>
              </el-col>
            </el-row>
            <div style="padding-left: 91px;line-height: 14px;font-size: 12px;color: #ff0000;">流转后执行：{{ form.toAfter=(toAfter_class?toAfter_class:"")+"."+(toAfter_method?toAfter_method:"") }}</div>
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
import { listToNode, getToNode, delToNode, addToNode, updateToNode } from "@/api/process/toNode";

export default {
  name: "ToNode",
  components: {
  },
  props: {
    fromNodeId:null,
    nodeList:[],
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 流转节点表格数据
      toNodeList: [],
      //单据状态
      billStateOptions:[],
      toAfter_class:null,
      toAfter_method:null,
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
        toNodeId: [
          { required: true, message: "流转节点不能为空", trigger: "blur" }
        ],
        toType: [
          { required: true, message: "流转类型不能为空", trigger: "blur" }
        ],
        billState: [
          { required: true, message: "流转后业务状态不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getBillStateList();
  },
  methods: {
    /** 查询流转节点列表 */
    getList() {
      if(!this.fromNodeId){
        this.toNodeList =[];
        this.loading = false;
        return;
      }
      this.loading = true;
      this.queryParams.fromNodeId=this.fromNodeId;
      listToNode(this.queryParams).then(response => {
        this.toNodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getBillStateList(){
      this.getDicts("bill_state").then(response => {
        this.billStateOptions = response.data;
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
        toNodeId: null,
        toType: null,
        billState: null,
        condition: null,
        toAfter: null,
        toAfter_class:null,
        toAfter_method:null,
        sortIndex: null,
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
      if(!this.fromNodeId){
        this.msgError("请选择上方节点！");
        return;
      }
      this.reset();
      this.open = true;
      this.title = "添加流转节点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getToNode(id).then(response => {
        this.form = response.data;
        if(this.form.toAfter){
          var toAfter=this.form.toAfter;
          this.toAfter_class=this.form.toAfter.substr(0,toAfter.lastIndexOf("."));
          this.toAfter_method=this.form.toAfter.substr(toAfter.lastIndexOf(".")+1);
        }
        this.open = true;
        this.title = "修改流转节点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if(!this.fromNodeId){
        this.msgError("请选择节点！");
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.fromNodeId=this.fromNodeId;
          if (this.form.id != null) {
            updateToNode(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addToNode(this.form).then(response => {
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
      this.$confirm('是否确认删除流转节点编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delToNode(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    getToTypeValue(type){
      switch (type){
        case "0":
          return "提交";
        case "1" :
          return "通过";
        case "2":
          return "退回";
        case "3":
          return "驳回";
      }
    },
    billStateFormat(row, column) {
      return this.selectDictLabel(this.billStateOptions, row.billState);
    }
  },
  watch:{
    fromNodeId(){
      this.getList();
    }
  }
};
</script>

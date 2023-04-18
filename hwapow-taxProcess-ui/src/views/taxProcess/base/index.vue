<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="纳税人名称" prop="colB">
          <el-input
            v-model="queryParams.colB"
            placeholder="请输入纳税人名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="是否已下载" prop="down">
          <el-input
            v-model="queryParams.down"
            placeholder="是否已下载"
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
        v-hasPermi="['taxProcess:base:add']"
      >新增
      </el-button>
      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['taxProcess:base:edit']"
      >修改
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['taxProcess:base:remove']"
      >删除
      </el-button>
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleDownLoad"
        v-hasPermi="['taxProcess:base:export']"
      >生成文件至C:\hwapow\uploadPath\tax\download
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </div>
    <el-main>
      <el-table v-loading="loading" height="100%" :data="baseList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column
          label="序号"
          type="index"
          width="80"
          align="center">
          <template scope="scope">
            <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="主键" align="center" prop="id" width="80px" show-overflow-tooltip/>
        <el-table-column label="纳税人识别号" align="center" prop="colA" show-overflow-tooltip/>
        <el-table-column label="纳税人名称" align="center" prop="colB" show-overflow-tooltip/>
        <el-table-column label="主管税务所" align="center" prop="colH" show-overflow-tooltip/>
        <el-table-column label="经营地址" align="center" prop="colL" show-overflow-tooltip/>
        <el-table-column label="法定代表人" align="center" prop="colM" show-overflow-tooltip/>
        <el-table-column label="法定代表人" align="center" prop="colN" show-overflow-tooltip/>
        <el-table-column label="财务负责人姓名" align="center" prop="colO" show-overflow-tooltip/>
        <el-table-column label="财务负责人移动电话" align="center" prop="colP" show-overflow-tooltip/>
        <el-table-column label="已下载" align="center" prop="down" show-overflow-tooltip width="80px"/>
        <el-table-column label="操作" align="center" class-name="small-padding" width="100px">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['taxProcess:base:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['taxProcess:base:remove']"
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
    <!-- 添加或修改基础信息表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-container class="fullContainer" direction="vertical">
        <div class="toolbar">
          <el-button size="mini" icon="el-icon-check" type="primary" @click="submitForm">确 定</el-button>
          <el-button size="mini" icon="el-icon-close" @click="cancel">取 消</el-button>
        </div>
        <el-main>
          <el-form size="mini" ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="纳税人名称" prop="colB">
              <el-input v-model="form.colB" placeholder="请输入纳税人名称"/>
            </el-form-item>
            <el-form-item label="纳税人识别号" prop="colA">
              <el-input v-model="form.colA" placeholder="请输入纳税人识别号"/>
            </el-form-item>
            <el-form-item label="主管税务所" prop="colH">
              <el-input v-model="form.colH" placeholder="请输入主管税务所"/>
            </el-form-item>
            <el-form-item label="街道乡镇" prop="colI">
              <el-input v-model="form.colI" placeholder="请输入街道乡镇"/>
            </el-form-item>
            <el-form-item label="注册地址" prop="colJ">
              <el-input v-model="form.colJ" placeholder="请输入注册地址"/>
            </el-form-item>
            <el-form-item label="注册地联系电话" prop="colK">
              <el-input v-model="form.colK" placeholder="请输入注册地联系电话"/>
            </el-form-item>
            <el-form-item label="经营地址" prop="colL">
              <el-input v-model="form.colL" placeholder="请输入经营地址"/>
            </el-form-item>
            <el-form-item label="法定代表人" prop="colM">
              <el-input v-model="form.colM" placeholder="请输入法定代表人"/>
            </el-form-item>
            <el-form-item label="法定代表人" prop="colN">
              <el-input v-model="form.colN" placeholder="请输入法定代表人"/>
            </el-form-item>
            <el-form-item label="财务负责人姓名" prop="colO">
              <el-input v-model="form.colO" placeholder="请输入财务负责人姓名"/>
            </el-form-item>
            <el-form-item label="财务负责人移动电话" prop="colP">
              <el-input v-model="form.colP" placeholder="请输入财务负责人移动电话"/>
            </el-form-item>
            <el-form-item label="办税人姓名" prop="colQ">
              <el-input v-model="form.colQ" placeholder="请输入办税人姓名"/>
            </el-form-item>
            <el-form-item label="办税人移动电话" prop="colR">
              <el-input v-model="form.colR" placeholder="请输入办税人移动电话"/>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-dialog>
  </el-container>
</template>

<script>
import {listBase, getBase, delBase, addBase, updateBase, exportBase, downloadFxbg} from "@/api/taxProcess/base";

export default {
  name: "Base",
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
      // 基础信息表表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        colB: null,
        colC: null,
        colA: null,
        colD: null,
        colE: null,
        colF: null,
        colG: null,
        colH: null,
        colI: null,
        colJ: null,
        colK: null,
        colL: null,
        colM: null,
        colN: null,
        colO: null,
        colP: null,
        colQ: null,
        colR: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询基础信息表列表 */
    getList() {
      this.loading = true;
      listBase(this.queryParams).then(response => {
        this.baseList = response.rows;
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
        colB: null,
        colC: null,
        colA: null,
        colD: null,
        colE: null,
        colF: null,
        colG: null,
        colH: null,
        colI: null,
        colJ: null,
        colK: null,
        colL: null,
        colM: null,
        colN: null,
        colO: null,
        colP: null,
        colQ: null,
        colR: null
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
      this.title = "添加基础信息表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBase(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基础信息表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBase(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBase(this.form).then(response => {
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
      this.$confirm('是否确认删除基础信息表编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delBase(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有基础信息表数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportBase(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    handleDownLoad(row) {
      const ids = row.id || this.ids;
      var n=0;
      if(ids.length>1000){
        this.msgSuccess("最多可勾选1000条数据，多的话可能报错");
        return;
      }
      for(var i in ids){
        downloadFxbg(ids[i]).then(res => {
          n++;
          console.log("第" + n + "/" + ids.length + "个文件生成成功！");
          //this.msgSuccess("第" + n + "/" + ids.length + "个文件生成成功！");
        })
      }
      //this.downloadFxbgZZZ(0, ids);
    },
    /*downloadFxbgZZZ(i, ids) {
      downloadFxbg(ids[i]).then(res => {
        //this.download(res.msg);//此处只调用生产文件，不下载，是为了节约时间，也是为了保证每个都生成成功，要是调用下载的话不一定成功
        i++;
        this.msgSuccess("第" + i + "/" + ids.length + "个文件生成成功！");
        if (i < ids.length) {
          this.downloadFxbgZZZ(i, ids);
        }else{
          this.getList();
        }
      })
    }*/
  }
};
</script>

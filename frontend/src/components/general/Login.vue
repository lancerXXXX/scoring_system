<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-08 20:26:41
-->

<template>
  <div class="login-form-wrapper">
    <el-form
      :model="LoginForm"
      status-icon
      :rules="rules"
      ref="LoginForm"
      label-width="100px"
      class="demo-LoginForm"
    >
      <el-form-item label="账户" prop="username">
        <input class="my-input" type="username" v-model="LoginForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <input class="my-input" type="password" v-model="LoginForm.password" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <div class="radio-container">
          <div class="tabs">
            <input
              type="radio"
              id="radio-1"
              name="tabs"
              checked
              v-model="LoginForm.usertype"
              value="0"
            />
            <label class="tab" for="radio-1">student</label>
            <input type="radio" id="radio-2" name="tabs" v-model="LoginForm.usertype" value="1" />
            <label class="tab" for="radio-2">teacher</label>
            <input type="radio" id="radio-3" name="tabs" v-model="LoginForm.usertype" value="2" />
            <label class="tab" for="radio-3">admin</label>
            <span class="glider"></span>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login()">提交</el-button>
        <el-button @click="resetForm('LoginForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <span>{{ usertype }}</span>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      LoginForm: {
        username: '',
        password: '',
        usertype: 0,
      },
    }
  },
  /** 将 auth 中的 getLoginStatus 映射为 getterLoginStatus */
  computed: {
    ...mapGetters('auth', {
      getterLoginStatus: 'getLoginStatus',
    }),
  },
  /** 将 auth 中的 login 映射为 actionLogin */
  methods: {
    ...mapActions('auth', {
      actionLogin: 'login',
    }),
    async login() {
      await this.actionLogin({
        username: this.LoginForm.username,
        password: this.LoginForm.password,
        usertype: this.LoginForm.usertype,
      }).then((res) => {
        console.log('登录结果', res);
        if (this.getterLoginStatus === 'success') {
          ElMessage.success({
            message: '登录成功',
            type: 'success',
          })
          this.$router.push('/student_home')
        } else {
          ElMessage.success({
            message: '登录失败',
            type: 'error',
          })
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style lang="less" scoped>
@import '../../assets/css/radioStyle.css';
.login-form-wrapper {
  // 样式
  background: rgba(255, 255, 255, 0.2);
  // box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(15.5px);
  -webkit-backdrop-filter: blur(15.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  // 结构
  margin: 0px auto;
  width: 40%;
  height: 99%;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 50px;
}
.my-input {
  // 样式
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(15.5px);
  -webkit-backdrop-filter: blur(15.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  // 结构
  width: 90%;
  border: 0px;
  border-radius: 16px !important;
  padding: 20px;
}

/deep/ .el-form-item__content {
  margin-right: 100px;
  display: flex;
  justify-content: center;
}
</style>

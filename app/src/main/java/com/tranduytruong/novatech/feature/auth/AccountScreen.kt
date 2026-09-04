package com.tranduytruong.novatech.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tranduytruong.novatech.ui.theme.AppBackground
import com.tranduytruong.novatech.ui.theme.BrandBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(vm: AccountViewModel = hiltViewModel()) {
    val state = vm.uiState
    Scaffold(
        topBar = { TopAppBar(title = { Text("Tài khoản", fontWeight = FontWeight.Bold) }) },
        containerColor = AppBackground,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding),
        ) {
            if (state.user == null) {
                AuthContent(
                    state = state,
                    onModeChange = vm::selectMode,
                    onNameChange = vm::updateFullName,
                    onEmailChange = vm::updateEmail,
                    onPasswordChange = vm::updatePassword,
                    onConfirmPasswordChange = vm::updateConfirmPassword,
                    onSubmit = vm::submit,
                )
            } else {
                ProfileContent(state = state, onSignOut = vm::signOut)
            }
        }
    }
}

@Composable
private fun AuthContent(
    state: AccountUiState,
    onModeChange: (AuthMode) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val isSignIn = state.mode == AuthMode.SIGN_IN

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(shape = CircleShape, color = Color(0xFFDBEAFE)) {
            Icon(
                imageVector = if (isSignIn) Icons.Default.Login else Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.padding(20.dp).size(48.dp),
                tint = BrandBlue,
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (isSignIn) "Chào mừng trở lại" else "Tham gia NovaTech",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            text = if (isSignIn) "Đăng nhập để tiếp tục mua sắm" else "Tạo tài khoản chỉ trong vài giây",
            color = Color(0xFF64748B),
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TextButton(onClick = { onModeChange(AuthMode.SIGN_IN) }, enabled = !state.isLoading) {
                Text("Đăng nhập", fontWeight = if (isSignIn) FontWeight.Bold else FontWeight.Normal)
            }
            TextButton(onClick = { onModeChange(AuthMode.SIGN_UP) }, enabled = !state.isLoading) {
                Text("Tạo tài khoản", fontWeight = if (!isSignIn) FontWeight.Bold else FontWeight.Normal)
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (!isSignIn) {
                    OutlinedTextField(
                        value = state.fullName,
                        onValueChange = onNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Họ và tên") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                }
                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    enabled = !state.isLoading,
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Mật khẩu") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPassword) "Ẩn mật khẩu" else "Hiện mật khẩu",
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    enabled = !state.isLoading,
                )
                if (!isSignIn) {
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Xác nhận mật khẩu") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                }

                state.error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                }
                state.success?.let {
                    Text(it, color = Color(0xFF15803D), fontSize = 14.sp)
                }

                Button(
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(16.dp),
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Icon(if (isSignIn) Icons.Default.Login else Icons.Default.PersonAdd, contentDescription = null)
                        Text(if (isSignIn) "  Đăng nhập" else "  Tạo tài khoản")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(state: AccountUiState, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(shape = CircleShape, color = Color(0xFFDBEAFE)) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.padding(22.dp).size(58.dp),
                tint = BrandBlue,
            )
        }
        Spacer(Modifier.height(14.dp))
        Text(
            state.profileName.ifBlank { "Khách hàng NovaTech" },
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(state.user?.email.orEmpty(), color = Color(0xFF64748B))

        Surface(
            modifier = Modifier.padding(top = 12.dp),
            color = Color(0xFFDCFCE7),
            shape = RoundedCornerShape(20.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF15803D))
                Text("Đã đăng nhập", color = Color(0xFF15803D), fontWeight = FontWeight.SemiBold)
            }
        }

        state.success?.let {
            Text(it, color = Color(0xFF15803D), modifier = Modifier.padding(top = 12.dp))
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Quyền lợi thành viên", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                AccountBenefit(Icons.Default.Shield, "Thông tin tài khoản được bảo vệ")
                AccountBenefit(Icons.Default.CheckCircle, "Theo dõi trạng thái đơn hàng")
                AccountBenefit(Icons.Default.Email, "Nhận thông báo và ưu đãi NovaTech")
            }
        }

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth().padding(top = 22.dp).height(50.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Text("  Đăng xuất")
        }
    }
}

@Composable
private fun AccountBenefit(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(icon, contentDescription = null, tint = BrandBlue)
        Text(text)
    }
}

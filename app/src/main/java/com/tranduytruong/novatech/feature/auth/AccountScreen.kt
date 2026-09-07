package com.tranduytruong.novatech.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.rounded.BrightnessAuto
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tranduytruong.novatech.core.domain.model.ThemeMode
import com.tranduytruong.novatech.ui.components.InlineMessage
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.NovaTechPrimaryButton
import com.tranduytruong.novatech.ui.components.glass.GlassButton
import com.tranduytruong.novatech.ui.components.glass.GlassCard
import com.tranduytruong.novatech.ui.components.glass.GlassChip
import com.tranduytruong.novatech.ui.components.glass.GlassTopBar
import com.tranduytruong.novatech.ui.theme.GlassTokens
import com.tranduytruong.novatech.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    vm: AccountViewModel = hiltViewModel(),
) {
    val state = vm.uiState

    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                GlassTopBar(
                    title = "Tài khoản",
                    subtitle = if (state.user != null) state.profileName.ifBlank { "Thành viên" } else null,
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                if (state.user == null) {
                    AuthContent(
                        state = state,
                        themeMode = themeMode,
                        onThemeModeChange = onThemeModeChange,
                        onModeChange = vm::selectMode,
                        onNameChange = vm::updateFullName,
                        onEmailChange = vm::updateEmail,
                        onPasswordChange = vm::updatePassword,
                        onConfirmPasswordChange = vm::updateConfirmPassword,
                        onSubmit = vm::submit,
                    )
                } else {
                    ProfileContent(
                        state = state,
                        themeMode = themeMode,
                        onThemeModeChange = onThemeModeChange,
                        onSignOut = vm::signOut,
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthContent(
    state: AccountUiState,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
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
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrandAvatar(if (isSignIn) Icons.Rounded.Login else Icons.Rounded.PersonAdd)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (isSignIn) "Chào mừng trở lại" else "Tham gia NovaTech",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )
            Text(
                text = if (isSignIn) "Đăng nhập để tiếp tục trải nghiệm" else "Tạo tài khoản chỉ trong vài giây",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Auth Mode Switcher
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            GlassChip(
                text = "Đăng nhập",
                isSelected = isSignIn,
                onClick = { onModeChange(AuthMode.SIGN_IN) },
                icon = Icons.Rounded.Login,
                modifier = Modifier.weight(1f),
            )
            GlassChip(
                text = "Tạo tài khoản",
                isSelected = !isSignIn,
                onClick = { onModeChange(AuthMode.SIGN_UP) },
                icon = Icons.Rounded.PersonAdd,
                modifier = Modifier.weight(1f),
            )
        }

        // Form Container
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = GlassTokens.ElevationHigh,
            contentPadding = PaddingValues(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                if (!isSignIn) {
                    OutlinedTextField(
                        value = state.fullName,
                        onValueChange = onNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Họ và tên") },
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        enabled = !state.isLoading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        ),
                    )
                }

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    enabled = !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    ),
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Mật khẩu") },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                contentDescription = if (showPassword) "Ẩn mật khẩu" else "Hiện mật khẩu",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    enabled = !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    ),
                )

                if (!isSignIn) {
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Xác nhận mật khẩu") },
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        ),
                    )
                }

                state.error?.let { InlineMessage(it, isError = true) }
                state.success?.let { InlineMessage(it, isError = false) }

                NovaTechPrimaryButton(
                    text = {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text(if (isSignIn) "Đăng nhập" else "Tạo tài khoản")
                        }
                    },
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    leadingIcon = {
                        if (!state.isLoading) {
                            Icon(
                                imageVector = if (isSignIn) Icons.Rounded.Login else Icons.Rounded.PersonAdd,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    },
                )
            }
        }

        ThemeSelector(themeMode, onThemeModeChange)
    }
}

@Composable
private fun ProfileContent(
    state: AccountUiState,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onSignOut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BrandAvatar(Icons.Rounded.Person)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = state.profileName.ifBlank { "Khách hàng NovaTech" },
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = state.user?.email.orEmpty(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Surface(
            color = SuccessGreen.copy(alpha = 0.15f),
            shape = RoundedCornerShape(20.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text = "Thành viên chính thức",
                    color = SuccessGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
        }

        state.success?.let { InlineMessage(it, isError = false) }

        // Member Benefits Box
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Text(
                    text = "Quyền lợi thành viên",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                AccountBenefit(Icons.Rounded.Security, "Thông tin tài khoản được bảo mật tuyệt đối")
                AccountBenefit(Icons.Rounded.CheckCircle, "Theo dõi lịch sử và trạng thái đơn hàng")
                AccountBenefit(Icons.Rounded.Email, "Nhận thông báo ưu đãi độc quyền NovaTech")
            }
        }

        ThemeSelector(themeMode, onThemeModeChange)

        // Sign Out Glass Button
        GlassButton(
            text = { Text("Đăng xuất tài khoản") },
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp),
                )
            },
        )
    }
}

@Composable
private fun ThemeSelector(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(20.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Giao diện ứng dụng",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )

            Text(
                text = "Tự động ghi nhớ trên thiết bị của bạn",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GlassChip(
                    text = "Hệ thống",
                    isSelected = themeMode == ThemeMode.SYSTEM,
                    onClick = { onThemeModeChange(ThemeMode.SYSTEM) },
                    icon = Icons.Rounded.BrightnessAuto,
                    modifier = Modifier.weight(1f),
                )
                GlassChip(
                    text = "Sáng",
                    isSelected = themeMode == ThemeMode.LIGHT,
                    onClick = { onThemeModeChange(ThemeMode.LIGHT) },
                    icon = Icons.Rounded.LightMode,
                    modifier = Modifier.weight(1f),
                )
                GlassChip(
                    text = "Tối",
                    isSelected = themeMode == ThemeMode.DARK,
                    onClick = { onThemeModeChange(ThemeMode.DARK) },
                    icon = Icons.Rounded.DarkMode,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BrandAvatar(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .background(
                brush = GlassTokens.primaryGradientBrush(),
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(46.dp),
            tint = Color.White,
        )
    }
}

@Composable
private fun AccountBenefit(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.60f),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp),
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

package msa.client.androidx_dagger2_mvvm_rx3.authentication

sealed class AuthenticatorUserFlow {
    data class SignIn(val email: String, val password: String) : AuthenticatorUserFlow()
    data class SignUp(val email: String, val password: String) : AuthenticatorUserFlow()
}
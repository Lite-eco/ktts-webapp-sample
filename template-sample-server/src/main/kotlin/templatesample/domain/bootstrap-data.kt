package templatesample.domain

// TODO[tmpl][user] naming start / initial / boot / launch / base Infos ?
// ConnectedUserInfos (logged is bad wording btw)
data class ApplicationBootstrapData(val env: ApplicationEnvironment, val userInfos: UserInfos?)

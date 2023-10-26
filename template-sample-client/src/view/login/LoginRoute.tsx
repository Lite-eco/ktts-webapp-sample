/** @jsxImportSource @emotion/react */
import { Errors } from '../../errors';
import {
  DevLoginCommandResponse,
  LoginCommandResponse
} from '../../generated/command/Commands.generated';
import { LoginResult, UserInfos } from '../../generated/domain/User.generated';
import { useI18n } from '../../hooks/i18n';
import { appContext } from '../../services/ApplicationContext';
import { useUserState } from '../../state/UserState';
import { colors } from '../../style/vars';
import { assertUnreachable } from '../../utils';
import { navigateTo } from '../../utils/routing-utils';
import { LoginRouteI18n } from './LoginRoute.i18n';
import { LoginForm, LoginFormInput } from './components/LoginForm';
import { css } from '@emotion/react';
import Button from '@mui/material/Button';
import { useState } from 'react';

export const LoginRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  const setUserInfos = useUserState(s => s.setUserInfos);
  const [loginResult, setLoginResult] = useState<LoginResult>();
  const connect = (userInfos: UserInfos) => {
    setUserInfos(userInfos);
    navigateTo({ name: 'Root' });
  };
  const login = (input: LoginFormInput) =>
    appContext.commandService
      .send<LoginCommandResponse>({
        objectType: 'LoginCommand',
        ...input
      })
      .then(r => {
        setLoginResult(r.result);
        switch (r.result) {
          case 'LoggedIn':
            if (!r.userInfos) {
              throw Errors._198c103e();
            }
            connect(r.userInfos);
            break;
          case 'MailNotFound':
          case 'BadPassword':
            break;
          default:
            assertUnreachable(r.result);
        }
      });
  const devLogin = (username: string) =>
    appContext.commandService
      .send<DevLoginCommandResponse>({
        objectType: 'DevLoginCommand',
        username
      })
      .then(r => connect(r.userInfos));
  const t = useI18n(LoginRouteI18n);
  return (
    <div
      css={css`
        margin: auto;
        max-width: 400px;
      `}
    >
      <h1
        css={css`
          text-align: center;
        `}
      >
        {t.Login()}
      </h1>
      <div>
        {loginResult !== 'LoggedIn' && !userInfos && (
          <LoginForm onSubmit={login} />
        )}
        {!userInfos && bootstrapData.env === 'Dev' && (
          <div
            css={css`
              margin-top: 20px;
            `}
          >
            <div
              css={css`
                display: inline;
                background: ${colors.clearGrey};
                border-radius: 4px;
                padding: 12px 20px;
                vertical-align: center;
              `}
            >
              <span
                css={css`
                  padding: 0 20px 0 0;
                `}
              >
                {t.devUserAuthent()}
              </span>
              <Button onClick={() => devLogin('user')}>{t.user()}</Button>
              <Button onClick={() => devLogin('admin')}>{t.admin()}</Button>
            </div>
          </div>
        )}
        {userInfos && (
          <div
            css={css`
              text-align: center;
            `}
          >
            {t.YouAreLoggedIn()}
          </div>
        )}
        {loginResult && (
          <div
            css={css`
              text-align: center;
              margin-top: 20px;
            `}
          >
            {(() => {
              switch (loginResult) {
                case 'LoggedIn':
                  return null;
                case 'MailNotFound':
                  return <div>{t.UserNotFound()}</div>;
                case 'BadPassword':
                  return <div>{t.BadPassword()}</div>;
                default:
                  assertUnreachable(loginResult);
              }
            })()}
          </div>
        )}
      </div>
    </div>
  );
};

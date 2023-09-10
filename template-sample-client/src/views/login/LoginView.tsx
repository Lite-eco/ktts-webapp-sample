/** @jsxImportSource @emotion/react */
import { Errors } from '../../errors';
import {
  DevLoginCommandResponse,
  LoginCommandResponse
} from '../../generated/command/Commands.generated';
import { LoginResult, UserInfos } from '../../generated/domain/User.generated';
import { useI18n } from '../../hooks/i18n';
import { useGoTo } from '../../routing/routing-utils';
import { appContext } from '../../services/ApplicationContext';
import { state } from '../../state/state';
import { colors } from '../../styles/vars';
import { assertUnreachable } from '../../utils';
import { LoginViewI18n } from './LoginView.i18n';
import { LoginForm, LoginFormInput } from './components/LoginForm';
import { css } from '@emotion/react';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { useRecoilState } from 'recoil';

export const LoginView = () => {
  const [userInfos, setUserInfos] = useRecoilState(state.userInfos);
  const [loginResult, setLoginResult] = useState<LoginResult>();
  const goTo = useGoTo();
  const connect = (userInfos: UserInfos) => {
    setUserInfos(userInfos);
    goTo(
      { name: 'Root' },
      {
        useTargetPath: true
      }
    );
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
  const t = useI18n(LoginViewI18n);
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

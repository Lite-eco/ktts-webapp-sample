/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Errors } from 'errors';
import { RegisterCommandResponse } from 'generated/command/Commands.generated';
import { RegisterResult } from 'generated/domain/User.generated';
import { useI18n } from 'hooks/i18n';
import { useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { useUserState } from 'state/UserState';
import { assertUnreachable } from 'utils';
import { navigateTo } from 'utils/routing-utils';
import { RegisterRouteI18n } from 'view/register/RegisterRoute.i18n';
import {
  RegisterForm,
  RegisterFormInput
} from 'view/register/components/RegisterForm';

export const RegisterRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  const setUserInfos = useUserState(s => s.setUserInfos);
  const [registerResult, setRegisterResult] = useState<RegisterResult>();
  const register = (input: RegisterFormInput) =>
    appContext.commandService
      .send<RegisterCommandResponse>({
        objectType: 'RegisterCommand',
        ...input
      })
      .then(r => {
        switch (r.result) {
          case 'Registered':
            if (!r.userInfos) {
              throw Errors._db434940();
            }
            setUserInfos(r.userInfos);
            navigateTo({ name: 'Root' });
            break;
          case 'MailAlreadyExists':
            break;
          default:
            assertUnreachable(r.result);
        }
        setRegisterResult(r.result);
      });
  const t = useI18n(RegisterRouteI18n);
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
        {t.Register()}
      </h1>
      <div
        css={css`
          width: 400px;
        `}
      >
        {registerResult !== 'Registered' && !userInfos && (
          <RegisterForm
            onSubmit={register}
            mailIsAlreadyTaken={registerResult === 'MailAlreadyExists'}
          />
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
      </div>
    </div>
  );
};

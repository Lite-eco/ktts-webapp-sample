/** @jsxImportSource @emotion/react */
import { Errors } from '../../errors';
import { RegisterCommandResponse } from '../../generated/command/Commands.generated';
import { RegisterResult } from '../../generated/domain/User.generated';
import { useGoTo } from '../../routing/routing-utils';
import { appContext } from '../../services/ApplicationContext';
import { state } from '../../state/state';
import { assertUnreachable } from '../../utils';
import { MainContainer } from '../containers/MainContainer';
import { t } from './RegisterView.i18n';
import { RegisterForm, RegisterFormInput } from './components/RegisterForm';
import { css } from '@emotion/react';
import { useState } from 'react';
import { useRecoilState } from 'recoil';

export const RegisterView = () => {
  const [userInfos, setUserInfos] = useRecoilState(state.userInfos);
  const [registerResult, setRegisterResult] = useState<RegisterResult>();
  const goTo = useGoTo();
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
            goTo({ name: 'Root' });
            break;
          case 'MailAlreadyExists':
            break;
          default:
            assertUnreachable(r.result);
        }
        setRegisterResult(r.result);
      });
  return (
    <MainContainer>
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
    </MainContainer>
  );
};

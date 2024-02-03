/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useI18n } from 'hooks/i18n';
import { useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { useUserState } from 'state/UserState';
import {
  LostPasswordFormMailInput,
  LostPasswordMailForm
} from 'view/lost-password/components/LostPasswordMailForm';
import { LostPasswordRouteI18n } from 'view/lost-password/LostPasswordRoute.i18n';

export const LostPasswordRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  const [displayFinalMessage, setDisplayFinalMessage] = useState(false);
  const sendLostPasswordMail = (input: LostPasswordFormMailInput) =>
    appContext.commandService
      .send({
        objectType: 'SendLostPasswordMailCommand',
        ...input
      })
      .then(() => {
        setDisplayFinalMessage(true);
      });
  const t = useI18n(LostPasswordRouteI18n);
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
        {t.LostPassword()}
      </h1>
      <div>
        {userInfos && (
          <div
            css={css`
              text-align: center;
            `}
          >
            {t.YouAreLoggedIn()}
          </div>
        )}
        {!userInfos && <LostPasswordMailForm onSubmit={sendLostPasswordMail} />}
        {displayFinalMessage && (
          <div
            css={css`
              margin: 10px 0;
            `}
          >
            {t.LostPasswordRequestMailSent()}
          </div>
        )}
      </div>
    </div>
  );
};

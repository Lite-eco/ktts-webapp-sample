/** @jsxImportSource @emotion/react */
import { useI18n } from '../../../hooks/i18n';
import { appContext } from '../../../services/ApplicationContext';
import { colors } from '../../../styles/vars';
import { LogoutMenuItemI18n } from './LogoutMenuItem.i18n';
import { css } from '@emotion/react';
import { MenuItem } from '@mui/material';
import { useRef } from 'react';

const logoutPath = '/logout';

export const LogoutMenuItem = () => {
  const t = useI18n(LogoutMenuItemI18n);
  const formElement = useRef<HTMLFormElement>(null);
  return (
    <MenuItem onClick={() => formElement.current?.submit()}>
      <div
        css={css`
          position: relative;
          padding: 6px 0;
          color: ${colors.grey};
        `}
      >
        {t.Logout()}
      </div>
      <form ref={formElement} method="post" action={logoutPath}>
        <input
          type="hidden"
          name={appContext.csrfTokenService.inputName}
          value={appContext.csrfTokenService.token}
        />
      </form>
    </MenuItem>
  );
};

/** @jsxImportSource @emotion/react */
import { nominal } from 'utils/nominal-class';
import { UserAccountOperationToken } from 'generated/domain/UserAccountOperationToken.generated';
import { ResetLostPasswordForm } from 'view/reset-lost-password/components/ResetLostPasswordForm';
import { useEffect } from 'react';
import { navigateTo } from 'utils/routing-utils';
import { css } from '@emotion/react';
import { useI18n } from 'hooks/i18n';
import { ResetLostPasswordRouteI18n } from 'view/reset-lost-password/ResetLostPasswordRoute.i18n';

const extractToken = (): UserAccountOperationToken | undefined => {
  const t = new URLSearchParams(window.location.search).get('authToken');
  return t ? nominal(t) : undefined;
};

export const ResetLostPasswordRoute = () => {
  const token = extractToken();
  const t = useI18n(ResetLostPasswordRouteI18n);
  useEffect(() => {
    if (!token) {
      navigateTo({ name: 'Root' });
    }
  }, [token]);
  if (!token) {
    return null;
  }
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
        {t.ResetPassword()}
      </h1>
      <ResetLostPasswordForm token={token} />
    </div>
  );
};

/** @jsxImportSource @emotion/react */
import { useI18n } from '../../hooks/i18n';
import { LoadingState } from '../../interfaces';
import { appContext } from '../../services/ApplicationContext';
import { LoadingStateButton } from '../LoadingButton';
import { LogoutButtonI18n } from './LogoutButton.i18n';
import { useState } from 'react';

const logoutPath = '/logout';

export const LogoutButton = () => {
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const t = useI18n(LogoutButtonI18n);
  return (
    <form
      method="post"
      action={logoutPath}
      onSubmit={() => setLoading('Loading')}
    >
      <input
        type="hidden"
        name={appContext.csrfTokenService.inputName}
        value={appContext.csrfTokenService.token}
      />
      <LoadingStateButton loadingState={loading} type="submit" variant="text">
        {t.Logout()}
      </LoadingStateButton>
    </form>
  );
};

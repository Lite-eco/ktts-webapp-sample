/** @jsxImportSource @emotion/react */
import { useI18n } from '../../hooks/i18n';
import { RouteLink } from '../../routing/RouteLink';
import { state } from '../../state/state';
import { RootViewI18n } from './RootView.i18n';
import { useRecoilValue } from 'recoil';

export const RootView = () => {
  const userInfos = useRecoilValue(state.userInfos);
  const t = useI18n(RootViewI18n);
  return (
    <>
      {!userInfos && (
        <RouteLink
          route={{
            name: 'Login'
          }}
        >
          {t.Login()}
        </RouteLink>
      )}
      {userInfos && <div>{t.YouAreConnected()}</div>}
    </>
  );
};

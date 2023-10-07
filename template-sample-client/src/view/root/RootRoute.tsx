/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../common-components/RouteLink';
import { useI18n } from '../../hooks/i18n';
import { state } from '../../state/state';
import { RootRouteI18n } from './RootRoute.i18n';
import { useRecoilValue } from 'recoil';

export const RootRoute = () => {
  const userInfos = useRecoilValue(state.userInfos);
  const t = useI18n(RootRouteI18n);
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

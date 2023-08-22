/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../routing/RouteLink';
import { state } from '../../state/state';
import { MainContainer } from '../containers/MainContainer';
import { t } from './RootView.i18n';
import { useRecoilValue } from 'recoil';

export const RootView = () => {
  const userInfos = useRecoilValue(state.userInfos);
  return (
    <MainContainer>
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
    </MainContainer>
  );
};

/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../routing/RouteLink';
import { state } from '../../state/state';
import { MainViewContainer } from '../containers/MainViewContainer';
import { NotFoundView } from '../not-found/NotFoundView';
import { t } from './AdminView.i18n';
import { Outlet, useMatches } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const AdminView = () => {
  const matches = useMatches();
  const displayLinks = matches.length === 1;
  const userInfos = useRecoilValue(state.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundView />;
  }
  return (
    <MainViewContainer>
      {displayLinks && (
        <>
          <RouteLink route={{ name: 'Admin/ManualCommand' }}>
            {t.ManualCommands()}
          </RouteLink>
          <br />
          <RouteLink route={{ name: 'Admin/UsersManagement' }}>
            {t.UsersManagement()}
          </RouteLink>
        </>
      )}
      <Outlet />
    </MainViewContainer>
  );
};

/** @jsxImportSource @emotion/react */
import { useI18n } from '../../hooks/i18n';
import { RouteLink } from '../../routing/RouteLink';
import { useTypedMatches } from '../../routing/routing-utils';
import { state } from '../../state/state';
import { NotFoundView } from '../not-found/NotFoundView';
import { AdminViewI18n } from './AdminView.i18n';
import { Outlet } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const AdminView = () => {
  const matches = useTypedMatches();
  const displayLinks = matches[matches.length - 1].id === 'Admin';
  const userInfos = useRecoilValue(state.userInfos);
  const t = useI18n(AdminViewI18n);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundView />;
  }
  return (
    <>
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
    </>
  );
};

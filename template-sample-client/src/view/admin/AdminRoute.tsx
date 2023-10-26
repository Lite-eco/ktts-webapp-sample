/** @jsxImportSource @emotion/react */
import { useUserState } from '../../state/UserState';
import { NotFoundRoute } from '../not-found/NotFoundRoute';
import { Outlet } from 'react-router-dom';

export const AdminRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundRoute />;
  }
  return <Outlet />;
};

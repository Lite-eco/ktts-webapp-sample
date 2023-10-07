/** @jsxImportSource @emotion/react */
import { state } from '../../state/state';
import { NotFoundRoute } from '../not-found/NotFoundRoute';
import { Outlet } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const AdminRoute = () => {
  const userInfos = useRecoilValue(state.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundRoute />;
  }
  return <Outlet />;
};

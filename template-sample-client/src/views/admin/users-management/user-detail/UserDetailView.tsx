/** @jsxImportSource @emotion/react */
import { UserInfos } from '../../../../generated/domain/User.generated';
import { GetUserInfosQueryResponse } from '../../../../generated/query/Queries.generated';
import { LoadingState } from '../../../../interfaces';
import { AdminUsersManagementUserDetailRoute } from '../../../../routing/ApplicationRoute.generated';
import { useTypedParams } from '../../../../routing/routing-utils';
import { appContext } from '../../../../services/ApplicationContext';
import { UsersManagementOutletContext } from '../UsersManagementView';
import { UserDetailDialog } from './components/UserDetailDialog';
import { useEffect, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';

export interface UsersManagementUserDetailOutletContext {
  userInfos: UserInfos | undefined;
  updateUserInfos: (userInfos: UserInfos) => void;
  loadingUserInfos: LoadingState;
}

export const UserDetailView = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailRoute>();
  const outletContext = useOutletContext<UsersManagementOutletContext>();
  const [userInfos, setUserInfos] = useState<UserInfos>();
  const [loadingUserInfos, setLoadingUserInfos] =
    useState<LoadingState>('Idle');
  const updateUserInfos = (userInfos: UserInfos) => {
    outletContext.updateUserInfos(userInfos);
    setUserInfos(userInfos);
  };
  useEffect(() => {
    setUserInfos(undefined);
    setLoadingUserInfos('Loading');
    appContext.queryService
      .send<GetUserInfosQueryResponse>({
        objectType: 'GetUserInfosQuery',
        userId: params.userId
      })
      .then(r => {
        setLoadingUserInfos('Idle');
        setUserInfos(r.userInfos);
      });
  }, [params.userId]);
  const context: UsersManagementUserDetailOutletContext = {
    userInfos,
    updateUserInfos,
    loadingUserInfos
  };
  return (
    <>
      <UserDetailDialog
        userInfos={userInfos}
        loadingUserInfos={loadingUserInfos}
      />
      <Outlet context={context} />
    </>
  );
};

/** @jsxImportSource @emotion/react */
import { UserInfos } from '../../../generated/domain/User.generated';
import { GetUsersQueryResponse } from '../../../generated/query/Queries.generated';
import { useI18n } from '../../../hooks/i18n';
import { LoadingState } from '../../../interfaces';
import { appContext } from '../../../services/ApplicationContext';
import { UsersManagementViewI18n } from './UsersManagementView.i18n';
import { UsersManagementTable } from './components/UsersManagementTable';
import { css } from '@emotion/react';
import { useSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';

export interface UsersManagementOutletContext {
  updateUserInfos: (userInfos: UserInfos) => void;
}

export const UsersManagementView = () => {
  const [users, setUsers] = useState<UserInfos[]>([]);
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const { enqueueSnackbar } = useSnackbar();
  // TODO would probably be better to reload data
  const updateUserInfos = (userInfos: UserInfos) =>
    setUsers([
      ...users.map(u => {
        if (u.id === userInfos.id) {
          return userInfos;
        } else {
          return u;
        }
      })
    ]);
  const t = useI18n(UsersManagementViewI18n);
  useEffect(() => {
    setLoading('Loading');
    appContext.queryService
      .send<GetUsersQueryResponse>({
        objectType: 'GetUsersQuery'
      })
      .then(r => {
        setUsers(r.users);
        setLoading('Idle');
      })
      .catch(() => {
        setLoading('Error');
        enqueueSnackbar(t.AnErrorOccurredWhileRetrievingData(), {
          variant: 'error'
        });
      });
  }, [enqueueSnackbar, t]);
  const context: UsersManagementOutletContext = { updateUserInfos };
  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        height: 100%;
      `}
    >
      <h1>{t.UsersManagement()}</h1>
      <UsersManagementTable users={users} loading={loading} />
      <Outlet context={context} />
    </div>
  );
};

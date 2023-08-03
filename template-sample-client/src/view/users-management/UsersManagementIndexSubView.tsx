/** @jsxImportSource @emotion/react */
import * as React from 'react';
import { UsersManagementTable } from './UsersManagementTable';
import { useEffect, useState } from 'react';
import { LoadingState } from '../../interfaces';
import { UserInfos } from '../../generated/domain/user';
import { appContext } from '../../ApplicationContext';
import { GetUsersListQueryResponse } from '../../generated/query/queries';
import { useSnackbar } from 'notistack';

export const UsersManagementIndexSubView = () => {
  const [users, setUsers] = useState<UserInfos[]>([]);
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const { enqueueSnackbar } = useSnackbar();
  useEffect(() => {
    setLoading('Loading');
    appContext
      .queryService()
      .send<GetUsersListQueryResponse>({
        objectType: 'GetUsersListQuery'
      })
      .then(r => {
        setUsers(r.users);
        setLoading('Idle');
      })
      .catch(() => {
        setLoading('Error');
        enqueueSnackbar('An error occured while retrieving data.', {
          variant: 'error'
        });
      });
  }, [enqueueSnackbar]);
  return <UsersManagementTable users={users} loading={loading} />;
};

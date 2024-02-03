/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import {
  DataGrid,
  GridColDef,
  GridRenderCellParams,
  GridValueFormatterParams,
  GridValueGetterParams
} from '@mui/x-data-grid';
import { RouteLink } from 'view/components/RouteLink';
import { adminIdDisplayChars } from 'domain/admin';
import { AdminUserInfos, Role } from 'generated/domain/User.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { colors } from 'style/vars';
import { CopyContentWidget } from 'view/admin/users-management/components/CopyContentWidget';
import { UsersManagementTableI18n } from 'view/admin/users-management/components/UsersManagementTable.i18n';

export const RoleChip = (props: { role: Role }) => (
  <span
    css={css`
      margin: 0 2px;
      padding: 4px 10px;
      background: ${colors.clearGrey};
      border-radius: 4px;
    `}
  >
    {props.role}
  </span>
);

export const UsersManagementTable = (props: {
  users: AdminUserInfos[];
  loading: LoadingStatus;
}) => {
  const t = useI18n(UsersManagementTableI18n);
  const columns: GridColDef[] = [
    {
      field: 'id',
      headerName: t.UserId(),
      renderCell: (p: GridRenderCellParams<AdminUserInfos>) => (
        <CopyContentWidget text={p.row.id} limitChars={adminIdDisplayChars} />
      ),
      flex: 1,
      maxWidth: 120,
      sortable: false,
      filterable: false
    },
    {
      field: 'email',
      headerName: t.Email(),
      renderCell: (p: GridRenderCellParams<AdminUserInfos>) => (
        <CopyContentWidget text={p.row.mail} />
      ),
      flex: 1,
      sortable: false,
      filterable: false
    },
    {
      field: 'displayName',
      headerName: t.DisplayName(),
      valueGetter: (p: GridValueGetterParams<AdminUserInfos>) =>
        p.row.displayName,
      flex: 1,
      sortable: false,
      filterable: false
    },
    {
      field: 'status',
      headerName: t.Status(),
      valueGetter: (p: GridValueGetterParams<AdminUserInfos>) => p.row.status,
      flex: 1,
      sortable: false,
      filterable: false
    },
    {
      field: 'role',
      headerName: t.Role(),
      renderCell: (p: GridRenderCellParams<AdminUserInfos>) => (
        <div>
          <RoleChip role={p.row.role} />
        </div>
      ),
      flex: 1,
      sortable: false,
      filterable: false
    },
    {
      field: 'signup date',
      headerName: t.SignupDate(),
      valueGetter: (p: GridValueGetterParams<AdminUserInfos>) =>
        new Date(p.row.signupDate),
      valueFormatter: (p: GridValueFormatterParams<Date>) =>
        p.value.toLocaleDateString() + ' ' + p.value.toLocaleTimeString(),
      flex: 1,
      sortable: true,
      filterable: false
    },
    {
      field: 'details',
      headerName: '',
      renderCell: (p: GridRenderCellParams<AdminUserInfos>) => (
        <RouteLink
          path={{
            name: 'Admin/UsersManagement/UserDetail',
            userId: p.row.id
          }}
          element="Button"
          variant="outlined"
        >
          {t.Details()}
        </RouteLink>
      ),
      flex: 1,
      maxWidth: 120,
      sortable: false,
      filterable: false
    }
  ];
  return (
    <DataGrid
      rows={props.users}
      getRowId={(c: AdminUserInfos) => c.id}
      columns={columns}
      loading={props.loading === 'Loading'}
      autoPageSize={true}
      css={css`
        .MuiDataGrid-columnHeaderTitle {
          font-weight: bold;
        }

        .MuiDataGrid-row {
          &:nth-of-type(odd) {
            background-color: ${colors.clearGrey2};
          }

          &:hover {
            background-color: ${colors.clearGrey};
          }
        }
      `}
    />
  );
};

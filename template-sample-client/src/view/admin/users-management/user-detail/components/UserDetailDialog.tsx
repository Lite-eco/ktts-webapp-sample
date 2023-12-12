/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../../../../common-components/RouteLink';
import { AdminUserInfos } from '../../../../../generated/domain/User.generated';
import { useI18n } from '../../../../../hooks/i18n';
import { LoadingState } from '../../../../../interfaces';
import { navigateTo } from '../../../../../utils/routing-utils';
import { CopyContentWidget } from '../../components/CopyContentWidget';
import { RoleChip } from '../../components/UsersManagementTable';
import { UserDetailDialogI18n } from './UserDetailDialog.i18n';
import { css } from '@emotion/react';
import { ExpandMore as ExpandMoreIcon } from '@mui/icons-material';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from '@mui/material';

export const UserDetailDialog = (props: {
  userInfos: AdminUserInfos | undefined;
  loadingUserInfos: LoadingState;
}) => {
  const close = () => navigateTo({ name: 'Admin/UsersManagement' });
  const t = useI18n(UserDetailDialogI18n);
  return (
    <Dialog
      open={true}
      onClose={close}
      maxWidth={'lg'}
      fullWidth={true}
      scroll="body"
    >
      <DialogTitle>{t.UserDetails()}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {props.loadingUserInfos === 'Loading' && (
            <div
              css={css`
                text-align: center;
              `}
            >
              <CircularProgress size={18} />
            </div>
          )}
          {props.userInfos && (
            <>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>{t.Field()}</TableCell>
                    <TableCell align="left">{t.Value()}</TableCell>
                    <TableCell />
                  </TableRow>
                </TableHead>
                <TableBody>
                  <TableRow
                    css={css`
                      &:last-child td,
                      &:last-child th {
                        border: 0;
                      }
                    `}
                  >
                    <TableCell component="th" scope="row">
                      {t.id()}
                    </TableCell>
                    <TableCell align="left">
                      <CopyContentWidget text={props.userInfos.id} />
                    </TableCell>
                    <TableCell align="left" />
                  </TableRow>
                  <TableRow
                    css={css`
                      &:last-child td,
                      &:last-child th {
                        border: 0;
                      }
                    `}
                  >
                    <TableCell component="th" scope="row">
                      {t.mail()}
                    </TableCell>
                    <TableCell align="left">
                      <CopyContentWidget text={props.userInfos.mail} />
                    </TableCell>
                    <TableCell align="left" />
                  </TableRow>
                  <TableRow
                    css={css`
                      &:last-child td,
                      &:last-child th {
                        border: 0;
                      }
                    `}
                  >
                    <TableCell component="th" scope="row">
                      {t.displayName()}
                    </TableCell>
                    <TableCell align="left">
                      {props.userInfos.displayName}
                    </TableCell>
                    <TableCell align="left" />
                  </TableRow>
                  <TableRow
                    css={css`
                      &:last-child td,
                      &:last-child th {
                        border: 0;
                      }
                    `}
                  >
                    <TableCell component="th" scope="row">
                      {t.status()}
                    </TableCell>
                    <TableCell align="left">{props.userInfos.status}</TableCell>
                    <TableCell align="left">
                      <RouteLink
                        path={{
                          name: 'Admin/UsersManagement/UserDetail/EditStatus',
                          userId: props.userInfos.id
                        }}
                        element="Button"
                        variant="outlined"
                      >
                        {t.Edit()}
                      </RouteLink>
                    </TableCell>
                  </TableRow>
                  <TableRow
                    css={css`
                      &:last-child td,
                      &:last-child th {
                        border: 0;
                      }
                    `}
                  >
                    <TableCell component="th" scope="row">
                      {t.role()}
                    </TableCell>
                    <TableCell align="left">
                      <RoleChip role={props.userInfos.role} />
                    </TableCell>
                    <TableCell align="left">
                      <RouteLink
                        path={{
                          name: 'Admin/UsersManagement/UserDetail/EditRole',
                          userId: props.userInfos.id
                        }}
                        element="Button"
                        variant="outlined"
                      >
                        {t.Edit()}
                      </RouteLink>
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              <div
                css={css`
                  margin: 10px 0;
                `}
              >
                <Accordion>
                  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                    {t.RawJson()}
                  </AccordionSummary>
                  <AccordionDetails>
                    <pre>{JSON.stringify(props.userInfos, null, 2)}</pre>
                  </AccordionDetails>
                </Accordion>
              </div>
            </>
          )}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={close}>{t.Close()}</Button>
      </DialogActions>
    </Dialog>
  );
};

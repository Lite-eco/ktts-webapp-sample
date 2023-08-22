/** @jsxImportSource @emotion/react */
import { CopyContentWidget } from '../../../../../common-components/CopyContentWidget';
import { UserInfos } from '../../../../../generated/domain/User.generated';
import { LoadingState } from '../../../../../interfaces';
import { RouteLink } from '../../../../../routing/RouteLink';
import { useGoTo } from '../../../../../routing/routing-utils';
import { RoleChip } from '../../components/UsersManagementTable';
import { t } from './UserDetailDialog.i18n';
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
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from '@mui/material';

export const UserDetailDialog = (props: {
  userInfos: UserInfos | undefined;
  loadingUserInfos: LoadingState;
}) => {
  const goTo = useGoTo();
  const close = () => goTo({ name: 'Admin/UsersManagement' });
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
              <TableContainer component={Paper}>
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
                        {t.roles()}
                      </TableCell>
                      <TableCell align="left">
                        {props.userInfos.roles.map(r => (
                          <RoleChip key={r} role={r} />
                        ))}
                      </TableCell>
                      <TableCell align="left">
                        <RouteLink
                          element="Button"
                          variant="outlined"
                          route={{
                            name: 'Admin/UsersManagement/UserDetail/EditRoles',
                            userId: props.userInfos.id
                          }}
                        >
                          {t.Edit()}
                        </RouteLink>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
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

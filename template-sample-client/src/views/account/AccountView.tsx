/** @jsxImportSource @emotion/react */
import { appContext } from '../../services/ApplicationContext';
import { MainViewContainer } from '../containers/MainViewContainer';
import { PasswordFormInput } from './components/PasswordForm';
import { UpdatePasswordDialogButton } from './components/UpdatePasswordDialogButton';

export const AccountView = () => {
  const onSubmit = (dto: PasswordFormInput) =>
    appContext.commandService
      .send({
        objectType: 'UpdatePasswordCommand',
        password: dto.password
      })
      .then(() => {});
  return (
    <MainViewContainer>
      <UpdatePasswordDialogButton onSubmit={onSubmit} />
    </MainViewContainer>
  );
};

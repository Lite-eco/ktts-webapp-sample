/** @jsxImportSource @emotion/react */
import { appContext } from '../../services/ApplicationContext';
import { MainContainer } from '../containers/MainContainer';
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
    <MainContainer>
      <UpdatePasswordDialogButton onSubmit={onSubmit} />
    </MainContainer>
  );
};

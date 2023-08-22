/** @jsxImportSource @emotion/react */
import { ControlledPasswordInput } from '../../../common-components/form/ControlledPasswordInput';
import { ClientUid } from '../../../domain/client-ids';
import { PlainStringPassword } from '../../../generated/domain/Security.generated';
import { asNominalString } from '../../../utils/nominal-class';
import { t } from './PasswordForm.i18n';
import { css } from '@emotion/react';
import { useForm } from 'react-hook-form';

export interface PasswordFormInput {
  password: PlainStringPassword;
}

export interface PasswordFormRawInput {
  password: string;
}

export const PasswordForm = (props: {
  formId: ClientUid;
  onSubmit: (dto: PasswordFormInput) => Promise<void>;
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<PasswordFormRawInput>();
  return (
    <form
      id={props.formId}
      onSubmit={handleSubmit(input =>
        props.onSubmit({
          password: asNominalString(input.password)
        })
      )}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="password"
          label={t.NewPassword()}
          control={control}
          errors={errors}
        />
      </div>
    </form>
  );
};
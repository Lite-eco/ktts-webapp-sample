/** @jsxImportSource @emotion/react */
import { LoadingStateButton } from '../../../common-components/LoadingButton';
import { ControlledPasswordInput } from '../../../common-components/form/ControlledPasswordInput';
import { ControlledTextInput } from '../../../common-components/form/ControlledTextInput';
import { PlainStringPassword } from '../../../generated/domain/Security.generated';
import { IsMailAlreadyTakenQueryResponse } from '../../../generated/query/Queries.generated';
import { useI18n } from '../../../hooks/i18n';
import { LoadingState } from '../../../interfaces';
import { appContext } from '../../../services/ApplicationContext';
import { colors } from '../../../style/vars';
import { nominal } from '../../../utils/nominal-class';
import { RegisterFormI18n } from './RegisterForm.i18n';
import { css } from '@emotion/react';
import { ChangeEvent, useState } from 'react';
import { useForm } from 'react-hook-form';

export interface RegisterFormInput {
  mail: string;
  password: PlainStringPassword;
  displayName: string;
}

export interface RegisterFormRawInput {
  mail: string;
  password: string;
  displayName: string;
}

export const RegisterForm = (props: {
  onSubmit: (dto: RegisterFormInput) => Promise<void>;
  mailIsAlreadyTaken: boolean;
}) => {
  const [mailIsAlreadyTaken, setMailIsAlreadyTaken] = useState(false);
  const checkMailAvailability = (
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const mail = event.target.value;
    appContext.queryService
      .send<IsMailAlreadyTakenQueryResponse>({
        objectType: 'IsMailAlreadyTakenQuery',
        mail
      })
      .then(r => {
        setMailIsAlreadyTaken(r.alreadyTaken);
      });
  };
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<RegisterFormRawInput>();
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const t = useI18n(RegisterFormI18n);
  return (
    <form
      onSubmit={handleSubmit(input => {
        setLoading('Loading');
        props
          .onSubmit({
            mail: input.mail,
            password: nominal(input.password),
            displayName: input.displayName
          })
          .then(() => setLoading('Idle'))
          .catch(() => setLoading('Error'));
      })}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledTextInput
          name="mail"
          label={t.Email()}
          onChange={checkMailAvailability}
          control={control}
          errors={errors}
        />
      </div>
      {(mailIsAlreadyTaken || props.mailIsAlreadyTaken) && (
        <div
          css={css`
            margin: 10px 0;
            color: ${colors.errorRed};
            font-weight: bold;
          `}
        >
          {t.EmailIsAlreadyTaken()}
        </div>
      )}
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="password"
          label={t.Password()}
          control={control}
          errors={errors}
        />
      </div>
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledTextInput
          name="displayName"
          label={t.DisplayName()}
          control={control}
          errors={errors}
        />
      </div>
      <LoadingStateButton loadingState={loading} type="submit">
        {t.Register()}
      </LoadingStateButton>
    </form>
  );
};

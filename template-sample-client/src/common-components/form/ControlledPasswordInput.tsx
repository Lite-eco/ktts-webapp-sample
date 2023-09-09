/** @jsxImportSource @emotion/react */
import { useI18n } from '../../hooks/i18n';
import { ControlledPasswordInputI18n } from './ControlledPasswordInput.i18n';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { TextField } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import { useState } from 'react';
import { Controller, FieldPath } from 'react-hook-form';
import { Control, FieldValues } from 'react-hook-form/dist/types';
import { FieldErrors } from 'react-hook-form/dist/types/errors';

export const ControlledPasswordInput = <
  TFieldValues extends FieldValues,
  TName extends FieldPath<TFieldValues>
>(props: {
  name: TName;
  label: string;
  control: Control<TFieldValues>;
  errors: FieldErrors<TFieldValues>;
  hideErrorMessage?: boolean;
}) => {
  const [showPassword, setShowPassword] = useState(false);
  const toggleShowPassword = () => setShowPassword(!showPassword);
  // TODO[tmpl] why ?? for mobile ?
  const handleMouseDownPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };
  const t = useI18n(ControlledPasswordInputI18n);
  return (
    <Controller
      name={props.name}
      control={props.control}
      rules={{ required: true }}
      render={({ field }) => (
        <TextField
          variant="outlined"
          fullWidth={true}
          {...field}
          label={props.label}
          error={!!props.errors[props.name]}
          size="small"
          type={showPassword ? 'text' : 'password'}
          helperText={
            props.errors[props.name] && !props.hideErrorMessage
              ? t.MandatoryField()
              : undefined
          }
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label={t.togglePasswordVisibility()}
                  onClick={toggleShowPassword}
                  onMouseDown={handleMouseDownPassword}
                  edge="end"
                >
                  {showPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            )
          }}
        />
      )}
    />
  );
};

/** @jsxImportSource @emotion/react */
import { globalStyles } from '../../styles/common-styles';
import { Global } from '@emotion/react';
import {
  createTheme,
  StyledEngineProvider,
  ThemeProvider
} from '@mui/material';
import StylesProvider from '@mui/styles/StylesProvider';
import { PropsWithChildren } from 'react';

const muiTheme = createTheme({
  typography: {
    // fontSize: fonts.baseSize,
    // htmlFontSize: fonts.baseSize
  }
});
export const StylesProviderContainer = (props: PropsWithChildren) => (
  <>
    <Global styles={[globalStyles]} />
    <StylesProvider injectFirst>
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={muiTheme}>{props.children}</ThemeProvider>
      </StyledEngineProvider>
    </StylesProvider>
  </>
);

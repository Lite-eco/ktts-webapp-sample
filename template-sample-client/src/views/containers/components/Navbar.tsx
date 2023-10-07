/** @jsxImportSource @emotion/react */
import { useI18n } from '../../../hooks/i18n';
import { ApplicationRoute } from '../../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../../routing/RouteLink';
import { buildPath } from '../../../routing/routing-utils';
import { state } from '../../../state/state';
import { colors } from '../../../styles/vars';
import { LogoutMenuItem } from './LogoutMenuItem';
import { NavbarI18n } from './Navbar.i18n';
import { css } from '@emotion/react';
import { Menu as MenuIcon } from '@mui/icons-material';
import { ListItemButton, ListItemText, Menu, MenuItem } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import { PropsWithChildren, useRef, useState } from 'react';
import { Link as RouterLink, useMatches } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const Navbar = () => {
  const userInfos = useRecoilValue(state.userInfos);
  const buttonElement = useRef<HTMLButtonElement>(null);
  const [open, setOpen] = useState(false);
  const close = () => setOpen(false);
  const t = useI18n(NavbarI18n);
  return (
    <div
      css={css`
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 36px;
        padding: 10px 20px;
        background: ${colors.grey};
      `}
    >
      <RouteLink
        addCss={css`
          color: ${colors.white};
          text-decoration: none;
          text-transform: uppercase;
        `}
        route={{
          name: 'Root'
        }}
      >
        {t.TemplateSample()}
      </RouteLink>
      <div
        css={css`
          display: flex;
        `}
      >
        <IconButton
          onClick={() => setOpen(!open)}
          ref={buttonElement}
          css={css`
            color: ${colors.white};
          `}
        >
          <MenuIcon />
        </IconButton>
        <Menu anchorEl={buttonElement.current} open={open} onClose={close}>
          {!userInfos && (
            <MenuLink
              route={{
                name: 'Login'
              }}
              label={t.Login()}
            />
          )}
          {!userInfos && (
            <MenuLink
              route={{
                name: 'Register'
              }}
              label={t.Register()}
            />
          )}
          {userInfos?.status === 'Active' && (
            <MenuLink
              route={{
                name: 'Account'
              }}
              label={t.Account()}
            />
          )}
          {userInfos?.role === 'Admin' && (
            <MenuLink
              route={{
                name: 'Admin'
              }}
              label={t.Admin()}
            />
          )}
          {userInfos && <LogoutMenuItem />}
        </Menu>
      </div>
    </div>
  );
};

const MenuLink = (props: { route: ApplicationRoute; label: string }) => {
  return (
    <MenuItem component={MatchRouterLink} route={props.route}>
      <ListItemButton>
        <ListItemText primary={props.label} />
      </ListItemButton>
    </MenuItem>
  );
};

const MatchRouterLink = (
  props: PropsWithChildren<{ route: ApplicationRoute }>
) => {
  const matches = useMatches();
  const to = buildPath(props.route);
  const doesMatch = matches[matches.length - 1].pathname === to;
  return (
    <div
      css={css`
        background: ${doesMatch ? colors.clearGrey : 'white'};
      `}
    >
      <RouterLink
        to={to}
        css={css`
          color: ${colors.grey};
          text-decoration: none;
        `}
      >
        {props.children}
      </RouterLink>
    </div>
  );
};

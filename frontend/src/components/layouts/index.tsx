import { Outlet } from 'react-router-dom';

type Props = {}

function MainLayout({}: Props) {
  return (
    <div>
        <span>Main LAYOUT</span>
        <Outlet />
    </div>
  )
}

export default MainLayout;
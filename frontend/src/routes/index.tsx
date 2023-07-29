import React, { ReactElement } from 'react';
import Loading from '../components/Loading';
import { Route, Routes } from 'react-router-dom';
import MainLayout from '../components/layouts';
import { PRIVATE_ROUTES } from './LazyLoading';

interface SuspenseWrapperProps {
    children: ReactElement
}

function SuspenseWrapper(props: SuspenseWrapperProps) {
    return (
        <React.Suspense fallback={<Loading />}>{props.children}</React.Suspense>
    )
}


function MainRoutes() {
    return (
        <Routes> 
            <Route path="/" element={<MainLayout />} />
            {PRIVATE_ROUTES.map((route) => 
                <Route
                    path={route.path}
                    key={route.path}
                    element={
                        <SuspenseWrapper>
                            <route.component />
                        </SuspenseWrapper>
                    }
                />
            )}
        </Routes>
    );
}

export default MainRoutes;
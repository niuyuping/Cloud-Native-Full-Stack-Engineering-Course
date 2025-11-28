import React from 'react';
import CalculatorForm from '../components/CalculatorForm';
import ResultsDisplay from '../components/ResultsDisplay';
import { PageLayout } from '../components/layouts/PageLayout';
import { Footer } from '../components/shared/Footer';

const HomePage: React.FC = () => {
    return (
        <PageLayout>
            <div className="p-6 sm:p-8">
                <CalculatorForm />
            </div>
            <ResultsDisplay />
            <div className="px-6 sm:px-8 pb-6">
                <Footer />
            </div>
        </PageLayout>
    );
};

export default HomePage;
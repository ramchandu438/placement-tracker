document.addEventListener('DOMContentLoaded', () => {
    // === METHOD 2: COSMIC CANVAS BACKDROP ===
    const canvas = document.getElementById('cosmic-canvas');
    if (canvas) {
        const ctx = canvas.getContext('2d');
        let particles = [];
        let shootingStars = [];

        const resizeCanvas = () => {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
        };
        resizeCanvas();
        window.addEventListener('resize', resizeCanvas);

        // Stardust Particle class
        class StardustParticle {
            constructor() {
                this.reset();
                this.y = Math.random() * canvas.height; // Random initial y to distribute stardust
            }
            reset() {
                this.x = Math.random() * canvas.width;
                this.y = canvas.height + 10;
                this.size = Math.random() * 2 + 0.5;
                this.speedX = Math.random() * 0.4 - 0.2;
                this.speedY = -(Math.random() * 0.5 + 0.1);
                this.opacity = Math.random() * 0.5 + 0.15;
            }
            update() {
                this.x += this.speedX;
                this.y += this.speedY;
                if (this.y < -10 || this.x < -10 || this.x > canvas.width + 10) {
                    this.reset();
                }
            }
            draw() {
                ctx.beginPath();
                ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
                ctx.fillStyle = `rgba(255, 255, 255, ${this.opacity})`;
                ctx.fill();
            }
        }

        // Shooting Star class
        class ShootingStar {
            constructor() {
                this.reset();
            }
            reset() {
                this.x = Math.random() * (canvas.width * 0.7);
                this.y = -20;
                this.length = Math.random() * 80 + 60;
                this.speedX = Math.random() * 6 + 4;
                this.speedY = Math.random() * 4 + 3;
                this.opacity = 0;
                this.state = 'fade-in'; // fade-in, fade-out, dead
            }
            update() {
                this.x += this.speedX;
                this.y += this.speedY;
                
                if (this.state === 'fade-in') {
                    this.opacity += 0.08;
                    if (this.opacity >= 0.8) {
                        this.opacity = 0.8;
                        this.state = 'fade-out';
                    }
                } else if (this.state === 'fade-out') {
                    this.opacity -= 0.03;
                    if (this.opacity <= 0) {
                        this.opacity = 0;
                        this.state = 'dead';
                    }
                }
            }
            draw() {
                if (this.opacity <= 0) return;
                const grad = ctx.createLinearGradient(this.x, this.y, this.x - this.speedX * 8, this.y - this.speedY * 8);
                grad.addColorStop(0, `rgba(255, 255, 255, ${this.opacity})`);
                grad.addColorStop(0.2, `rgba(244, 63, 94, ${this.opacity * 0.6})`); // Crimson trace highlight
                grad.addColorStop(1, 'rgba(255, 255, 255, 0)');
                ctx.beginPath();
                ctx.strokeStyle = grad;
                ctx.lineWidth = 1.8;
                ctx.lineCap = 'round';
                ctx.moveTo(this.x, this.y);
                ctx.lineTo(this.x - this.speedX * 6, this.y - this.speedY * 6);
                ctx.stroke();
            }
        }

        // Initialize particles
        for (let i = 0; i < 70; i++) {
            particles.push(new StardustParticle());
        }

        const animate = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            
            particles.forEach(p => {
                p.update();
                p.draw();
            });

            // Random stardust shooting star generator
            if (shootingStars.length < 1 && Math.random() < 0.0035) {
                shootingStars.push(new ShootingStar());
            }

            shootingStars.forEach((star, index) => {
                star.update();
                star.draw();
                if (star.state === 'dead') {
                    shootingStars.splice(index, 1);
                }
            });

            requestAnimationFrame(animate);
        };
        animate();
    }

    // === METHOD 4: STATS NUMBERS ODOMETER ===
    const odometers = document.querySelectorAll('.stat-value.odometer');
    odometers.forEach(odo => {
        const targetValue = parseInt(odo.getAttribute('data-target') || '0', 10);
        let startTimestamp = null;
        const duration = 1200; // Count-up speed (ms)

        const step = (timestamp) => {
            if (!startTimestamp) startTimestamp = timestamp;
            const progress = Math.min((timestamp - startTimestamp) / duration, 1);
            
            // Ease out cubic easing formula
            const easeProgress = 1 - Math.pow(1 - progress, 3);
            const currentValue = Math.floor(easeProgress * targetValue);
            
            odo.textContent = currentValue;
            
            if (progress < 1) {
                window.requestAnimationFrame(step);
            } else {
                odo.textContent = targetValue;
                odo.style.textShadow = '0 0 15px rgba(255, 255, 255, 0.4)';
                setTimeout(() => {
                    odo.style.textShadow = '';
                }, 600);
            }
        };
        
        if (targetValue > 0) {
            window.requestAnimationFrame(step);
        } else {
            odo.textContent = '0';
        }
    });

    // === METHOD 5: CLICK TO EXPAND PIPELINE DRAWER ===
    const appRows = document.querySelectorAll('.app-table tbody .app-row');
    appRows.forEach(row => {
        row.addEventListener('click', (e) => {
            // Avoid expanding if user clicks buttons/actions
            if (e.target.closest('.actions-cell') || e.target.closest('a') || e.target.closest('button')) {
                return;
            }

            const appId = row.getAttribute('data-id');
            const drawer = document.getElementById(`drawer-${appId}`);
            if (!drawer) return;

            const isExpanded = row.classList.contains('expanded');
            
            // Close other open drawers first (accordion style)
            document.querySelectorAll('.app-table tbody .app-row.expanded').forEach(otherRow => {
                if (otherRow !== row) {
                    otherRow.classList.remove('expanded');
                    const otherDrawer = document.getElementById(`drawer-${otherRow.getAttribute('data-id')}`);
                    if (otherDrawer) {
                        otherDrawer.classList.remove('expanded');
                        setTimeout(() => {
                            if (!otherRow.classList.contains('expanded')) {
                                otherDrawer.style.display = 'none';
                            }
                        }, 500);
                    }
                }
            });

            // Toggle current row & drawer
            if (isExpanded) {
                row.classList.remove('expanded');
                drawer.classList.remove('expanded');
                setTimeout(() => {
                    if (!row.classList.contains('expanded')) {
                        drawer.style.display = 'none';
                    }
                }, 500);
            } else {
                row.classList.add('expanded');
                drawer.style.display = 'table-row';
                // Trigger reflow to activate slide down transition
                drawer.offsetHeight;
                drawer.classList.add('expanded');
                initializePipelineRoadmap(drawer);
            }
        });
    });

    const initializePipelineRoadmap = (drawer) => {
        const flowElement = drawer.querySelector('.pipeline-flow');
        if (!flowElement) return;

        const status = flowElement.getAttribute('data-status');
        // Fetch rounds cleared from cell 6
        const roundsCleared = parseInt(drawer.closest('.drawer-row').previousElementSibling.querySelector('td:nth-child(6)').textContent.trim(), 10) || 0;
        
        const steps = flowElement.querySelectorAll('.pipeline-step');
        const connectors = flowElement.querySelectorAll('.pipeline-connector');
        
        // Reset classes
        steps.forEach(s => s.classList.remove('completed', 'active', 'rejected'));
        connectors.forEach(c => c.classList.remove('completed', 'active'));

        const stageList = ['APPLIED', 'OA', 'INTERVIEW', 'SELECTED'];
        let currentStageIndex = stageList.indexOf(status);

        if (status === 'REJECTED') {
            // Determine stage rejection based on rounds cleared
            let maxCompletedIndex = 0; // applied is index 0
            if (roundsCleared === 1) maxCompletedIndex = 1; // cleared applied & OA
            if (roundsCleared >= 2) maxCompletedIndex = 2; // cleared applied, OA, & Interview
            
            // Mark passed steps as completed
            for (let i = 0; i <= maxCompletedIndex; i++) {
                if (steps[i]) steps[i].classList.add('completed');
                if (connectors[i - 1]) connectors[i - 1].classList.add('completed');
            }
            
            // The next stage was where the failure occurred (rejected)
            const rejectedIndex = maxCompletedIndex + 1;
            if (steps[rejectedIndex]) {
                steps[rejectedIndex].classList.add('rejected');
                const iconSpan = steps[rejectedIndex].querySelector('.step-icon');
                if (iconSpan) iconSpan.textContent = '❌';
                const labelSpan = steps[rejectedIndex].querySelector('.step-label');
                if (labelSpan) labelSpan.textContent = 'Rejected';
            }
            if (connectors[rejectedIndex - 1]) {
                connectors[rejectedIndex - 1].style.background = 'linear-gradient(90deg, #10b981 30%, #f43f5e 100%)';
            }
        } else {
            // Standard tracking flow
            for (let i = 0; i < steps.length; i++) {
                if (i < currentStageIndex) {
                    steps[i].classList.add('completed');
                    if (connectors[i - 1]) connectors[i - 1].classList.add('completed');
                } else if (i === currentStageIndex) {
                    steps[i].classList.add('active');
                    if (connectors[i - 1]) {
                        connectors[i - 1].classList.add('active');
                    }
                }
            }
            if (currentStageIndex > 0 && connectors[currentStageIndex - 1]) {
                connectors[currentStageIndex - 1].classList.add('completed');
            }
        }
    };

    // === CLIENT SIDE SEARCH & FILTER LOGIC ===
    const searchInput = document.getElementById('searchCompany');
    const statusFilter = document.getElementById('filterStatus');
    const emptyState = document.getElementById('tableEmptyState');

    const filterTable = () => {
        const searchQuery = searchInput ? searchInput.value.toLowerCase().trim() : '';
        const selectedStatus = statusFilter ? statusFilter.value : 'ALL';
        let visibleCount = 0;

        const mainRows = document.querySelectorAll('.app-table tbody .app-row');
        mainRows.forEach(row => {
            const company = row.querySelector('.company-cell').textContent.toLowerCase();
            const role = row.querySelector('.role-cell').textContent.toLowerCase();
            const statusBadge = row.querySelector('.status-badge');
            const status = statusBadge ? statusBadge.getAttribute('data-status') : '';

            const matchesSearch = company.includes(searchQuery) || role.includes(searchQuery);
            const matchesStatus = selectedStatus === 'ALL' || status === selectedStatus;

            const drawer = document.getElementById(`drawer-${row.getAttribute('data-id')}`);

            if (matchesSearch && matchesStatus) {
                row.style.display = '';
                visibleCount++;
                // Sync drawer visibility: display if row is expanded, hide otherwise
                if (row.classList.contains('expanded') && drawer) {
                    drawer.style.display = 'table-row';
                } else if (drawer) {
                    drawer.style.display = 'none';
                }
            } else {
                row.style.display = 'none';
                if (drawer) {
                    drawer.style.display = 'none';
                }
            }
        });

        if (emptyState) {
            if (visibleCount === 0 && mainRows.length > 0) {
                emptyState.style.display = '';
                const colCount = document.querySelectorAll('.app-table th').length;
                const emptyCell = emptyState.querySelector('td');
                if (emptyCell) emptyCell.setAttribute('colspan', colCount);
            } else {
                emptyState.style.display = 'none';
            }
        }
    };

    if (searchInput) searchInput.addEventListener('input', filterTable);
    if (statusFilter) statusFilter.addEventListener('change', filterTable);

    // Confirm deletion actions
    const deleteButtons = document.querySelectorAll('.btn-action-delete, .btn-danger-confirm');
    deleteButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            event.stopPropagation(); // Avoid triggering row expansion drawer
            const companyName = button.getAttribute('data-company') || 'this application';
            const confirmed = confirm(`Are you sure you want to permanently delete the application for ${companyName}?`);
            if (!confirmed) {
                event.preventDefault();
            }
        });
    });

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.6s ease';
            setTimeout(() => {
                alert.remove();
            }, 600);
        }, 5000);
    });

    // Specular shine mouse tracking
    const glassCards = document.querySelectorAll('.glass-card');
    glassCards.forEach(card => {
        card.addEventListener('mousemove', (e) => {
            const rect = card.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            card.style.setProperty('--mouse-x', `${x}px`);
            card.style.setProperty('--mouse-y', `${y}px`);
        });
    });
});

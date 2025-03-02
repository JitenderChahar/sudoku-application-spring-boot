$(document).ready(function () {
    function generateSudokuBoard(grid, size) {
        $('#sudoku-board').empty().attr('data-size', size);
        let subGridSize = Math.sqrt(size);
        $('#sudoku-board').css({
            'grid-template-columns': `repeat(${size}, 50px)`,
            'grid-template-rows': `repeat(${size}, 50px)`
        });

        grid.forEach((row, rowIndex) => {
            row.forEach((cell, colIndex) => {
                let input = $(`<input type="text" class="sudoku-cell" data-row="${rowIndex}" data-col="${colIndex}" value="${cell !== 0 ? cell : ''}" ${cell !== 0 ? 'disabled' : ''}/>`);
                if (cell !== 0) {
                    input.addClass('prefilled'); // Different color for pre-filled cells
                }
                input.on("click", function () {
                    highlightCells(rowIndex, colIndex, size, subGridSize);
                });
                $('#sudoku-board').append(input);
            });
        });
    }

    function highlightCells(row, col, size, subGridSize) {
        $(".sudoku-cell").removeClass("highlight");

        $(".sudoku-cell").each(function () {
            let r = $(this).data("row");
            let c = $(this).data("col");

            if (r === row || c === col || (Math.floor(r / subGridSize) === Math.floor(row / subGridSize) && Math.floor(c / subGridSize) === Math.floor(col / subGridSize))) {
                $(this).addClass("highlight");
            }
        });
    }

    function showMessage(text, isError = false) {
        $('#message').text(text).removeClass('success error').addClass(isError ? 'error' : 'success');
        setTimeout(() => {
            $('#message').text("");
        }, 3000);
    }

    function loadSudoku(size, difficulty) {
        $.get("/sudoku/generate", { size: size, difficulty: difficulty }, function (data) {
            generateSudokuBoard(data.grid, size);
            showMessage(data.message, false);
        });
    }

    // Load grid when dropdown changes
    $('#size, #difficulty').change(function () {
        let size = parseInt($('#size').val());
		let difficulty = $('#difficulty').val();
        loadSudoku(size, difficulty);
    });

    // Fix Generate button
    $('#generate-btn').click(function () {
        let size = parseInt($('#size').val());
		let difficulty = $('#difficulty').val();
        loadSudoku(size, difficulty);
    });

    $('#solve-btn').click(function () {
        let size = parseInt($('#size').val());
        let puzzle = [];
        $('#sudoku-board input').each(function () {
            const value = $(this).val();
            puzzle.push(value ? parseInt(value) : 0);
        });
        puzzle = chunkArray(puzzle, size);

        $.ajax({
            url: "/sudoku/solve",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ puzzle: puzzle }),
            success: function (data) {
                generateSudokuBoard(data.grid, size);
                showMessage(data.message, false);
            }
        });
    });

    $('#validate-btn').click(function () {
        let size = parseInt($('#size').val());
        let puzzle = [];
        $('#sudoku-board input').each(function () {
            const value = $(this).val();
            puzzle.push(value ? parseInt(value) : 0);
        });
        puzzle = chunkArray(puzzle, size);

        $.ajax({
            url: "/sudoku/validate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ puzzle: puzzle }),
            success: function (data) {
                let isError = !data.includes("Congratulation");
                showMessage(data, isError);
            }
        });
    });
	
	$(document).click(function (event) {
	    // Check if the clicked element is NOT inside the Sudoku grid
	    if (!$(event.target).closest("#sudoku-board, .select-container, button").length) {
	        $(".highlight").removeClass("highlight"); // Remove all highlights
	    }
	});

    function chunkArray(array, chunkSize) {
        const result = [];
        for (let i = 0; i < array.length; i += chunkSize) {
            result.push(array.slice(i, i + chunkSize));
        }
        return result;
    }

    // Load 9x9 Sudoku by default
    loadSudoku(9, "MEDIUM");
});
